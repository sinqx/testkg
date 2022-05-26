package ivt.tp.project2.service;

import ivt.tp.project2.entity.TestResult;
import ivt.tp.project2.entity.User;
import ivt.tp.project2.model.AnswerModel;

import java.util.List;

public interface TestResultService {
    TestResult save(TestResult testResult);
    TestResult calculateAndSave(AnswerModel answer);
    TestResult findById(Long id);
    List<TestResult> getAllTests(TestResult testResult);
    List<TestResult> getAllTestsByUser(User user);
    List<TestResult> getAllTestsResults(Long id);
    String deleteById(Long id);
}
