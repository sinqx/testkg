package ivt.tp.project2.repository;

import ivt.tp.project2.entity.Test;
import ivt.tp.project2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long>{
    List<Test> findAllByCreatorUser(User user);
}
