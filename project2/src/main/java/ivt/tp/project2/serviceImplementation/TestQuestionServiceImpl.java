package ivt.tp.project2.serviceImplementation;

import ivt.tp.project2.entity.QuestionOption;
import ivt.tp.project2.entity.TestQuestion;
import ivt.tp.project2.exception.ObjectNotFoundException;
import ivt.tp.project2.repository.TestQuestionRepository;
import ivt.tp.project2.service.QuestionOptionService;
import ivt.tp.project2.service.TestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class TestQuestionServiceImpl implements TestQuestionService {
    @Autowired
    private TestQuestionRepository testQuestionRepository;

    @Autowired
    private QuestionOptionService questionOptionService;

    @Override
    public TestQuestion save(TestQuestion testQuestions) {
        testQuestions.getQuestionOptions().add(
                new QuestionOption(null,
                       null,
                        testQuestions.getCorrectOption()));
        Collections.shuffle(testQuestions.getQuestionOptions());
        for (int i = 0; i < testQuestions.getQuestionOptions().size(); i++) {
            testQuestions.getQuestionOptions().get(i).setTestQuestion(testQuestions);
        }
       return testQuestionRepository.save(testQuestions);

    }


    @Override
    public TestQuestion findById(Long id) {
        return testQuestionRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Question with id \"" + id + "\" doesn't exist"));
    }

    @Override
    public List<TestQuestion> findAllQuestionByTestId(Long id) {
        try {
            return testQuestionRepository.findAllQuestionByTestId(id);
        } catch (NullPointerException ignored) {
            throw new ObjectNotFoundException("List is empty");
        }
    }

//    @Override
//    public List<String> findAllCorrectOptionByTestId(Long id) {
//        try {
//            List <TestQuestion> questions = testQuestionRepository.findAllCorrectOptionByTest_Id(id);
//            List<String> answers = questions.
//            return
//        } catch (NullPointerException ignored) {
//            throw new ObjectNotFoundException("List is empty");
//        }
//    }

    @Override
    public String deleteById(Long id) {
        TestQuestion testQuestions = findById(id);
        if (testQuestions == null) {
            throw new ObjectNotFoundException("Test questions with id \"" + id + "\" doesn't exist");
        } else {
            testQuestionRepository.delete(testQuestions);
            return "Event with id \"" + id + "\" is deleted";
        }
    }
}
