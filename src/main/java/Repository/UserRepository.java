package Repository;

import Entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Registration,Long> {
    Optional<Registration> findByEmail(String email);
}
