package Service;

import Entity.Registration;
import Repository.UserRepository;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;

import java.util.Optional;

public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final BcryptPassword4jPasswordEncoder encoder=new BcryptPassword4jPasswordEncoder();
    public AuthServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Override
    public void register(String fullName,String email,String password){
        if(userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("Email already Exists");
        }
        Registration user=new Registration();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public boolean login(String email, String password) {
        Optional<Registration> userOpt=userRepository.findByEmail(email);
        if(userOpt.isEmpty())
        return false;

        Registration user=userOpt.get();
        return encoder.matches(password,user.getPassword());
    }
}
