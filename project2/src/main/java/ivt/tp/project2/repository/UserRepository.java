package ivt.tp.project2.repository;

import ivt.tp.project2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String username);
    Optional<User> findByEmail(String username);
    List<User> findByFullNameContainingIgnoringCase(String username);
}
