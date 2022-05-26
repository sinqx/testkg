package ivt.tp.project2.service;

import ivt.tp.project2.entity.Test;
import ivt.tp.project2.entity.User;

import java.util.List;

public interface TestService {
    Test save(Test test);
    Test findById(Long id);
    List<Test> findAllUserTests(User user);
    List<Test> getAllTests();
    String deleteById(Long id);
}
