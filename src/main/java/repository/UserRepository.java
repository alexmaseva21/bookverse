package repository;

import model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // Used to check if a username is already taken during registration
    boolean existsByUsername(String username);

    // Used to check if an email is already registered
    boolean existsByEmail(String email);

    // Used later during login to find the user by their username
    Optional<User> findByUsername(String username);
}
