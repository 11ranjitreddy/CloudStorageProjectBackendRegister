package Controller;


import DTOs.LoginRequest;
import DTOs.RegisterRequest;
import Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService=authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        authService.register(
                request.getFullName(),
                request.getEmail(),
                request.getPassword()
        );
        return ResponseEntity.ok("Registration successful");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){

        boolean success=authService.login(
                request.getEmail(),
                request.getPassword()
        );
        if(!success)
            return ResponseEntity.badRequest().body("Invalid email or password");

        return ResponseEntity.ok("Login successful");
    }


}
