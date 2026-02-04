package com.Cloud.Storage.Service;

public interface AuthService {
    void register(String fullName,String email,String password);

    boolean login(String email,String password);
    boolean verifyOtp(String email,String otp);
}
