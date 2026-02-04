package com.Cloud.Storage.Controller;

import com.Cloud.Storage.DTOs.LoginRequest;
import com.Cloud.Storage.DTOs.RegisterRequest;
import com.Cloud.Storage.DTOs.VerifyOtpRequest;
import com.Cloud.Storage.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ✅ Register (send OTP)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        authService.register(
                request.getFullName(),
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok("Registration successful. OTP sent.");
    }

    // ✅ Verify OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest request) {

        boolean verified = authService.verifyOtp(
                request.getEmail(),
                request.getOtp()
        );

        if (!verified) {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }

        return ResponseEntity.ok("Email verified successfully");
    }

    // ✅ Login (only after verification)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        boolean success = authService.login(
                request.getEmail(),
                request.getPassword()
        );
        if (!success) {
            return ResponseEntity.badRequest().body("Invalid credentials or email not verified");
        }
        return ResponseEntity.ok("Login successful");
    }
}
