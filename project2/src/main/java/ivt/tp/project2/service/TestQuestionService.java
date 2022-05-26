package ivt.tp.project2.service;

import ivt.tp.project2.entity.TestQuestion;

import java.util.List;

public interface TestQuestionService {
    TestQuestion save(TestQuestion testQuestions);
    TestQuestion findById(Long id);
    List<TestQuestion> findAllQuestionByTestId(Long id);
 //   List<TestQuestion> findAllCorrectOptionByTestId(Long id);
    String deleteById(Long id);
}
