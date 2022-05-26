package ivt.tp.project2.repository;

import ivt.tp.project2.entity.TestResult;
import ivt.tp.project2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    List<TestResult> findAllByUser(User user);
    List<TestResult> findAllByTestId(Long id);
}
