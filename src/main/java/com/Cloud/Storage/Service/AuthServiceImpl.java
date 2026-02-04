package com.Cloud.Storage.Service;

import com.Cloud.Storage.Entity.Registration;
import com.Cloud.Storage.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final EmailService emailService;

    public AuthServiceImpl(UserRepository userRepository,EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService=emailService;
    }

    @Override
    public void register(String fullName, String email, String password) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        Registration user = new Registration();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setOtp(generateOtp());
        user.setVerified(false);

        userRepository.save(user);
        emailService.sendOtpEmail(email,user.getOtp());

    }

    @Override
    public boolean verifyOtp(String email, String otp) {

        Registration user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return false;

        if (otp.equals(user.getOtp())) {
            user.setVerified(true);
            user.setOtp(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean login(String email, String password) {

        Optional<Registration> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return false;

        Registration user = userOpt.get();

        if (!user.isVerified()) return false;

        return encoder.matches(password, user.getPassword());
    }

    private String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }
}
