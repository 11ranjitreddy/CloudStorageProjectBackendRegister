package Service;

public interface AuthService {
    void register(String fullName,String email,String password);

    boolean login(String email,String password);
}
