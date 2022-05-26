package ivt.tp.project2.serviceImplementation;

import ivt.tp.project2.entity.TestQuestion;
import ivt.tp.project2.entity.TestResult;
import ivt.tp.project2.entity.User;
import ivt.tp.project2.exception.ObjectNotFoundException;
import ivt.tp.project2.model.AnswerModel;
import ivt.tp.project2.repository.TestResultRepository;
import ivt.tp.project2.service.TestQuestionService;
import ivt.tp.project2.service.TestResultService;
import ivt.tp.project2.service.TestService;
import ivt.tp.project2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestResultServiceImpl implements TestResultService {
    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TestQuestionService testQuestionService;

    @Autowired
    private TestService testService;

    @Override
    public TestResult save(TestResult testResult) {
        return testResultRepository.save(testResult);
    }

    @Override
    public TestResult calculateAndSave(AnswerModel answerModel) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByLogin(username);
        List<TestQuestion> testQuestion = testQuestionService.findAllQuestionByTestId(answerModel.getTestId());
        List<String> userAnswers = new ArrayList<>(answerModel.getAnswers());

        long userMark = 0, maxMark = 0;
        for (int i = 0; i < testQuestion.size(); i++) {
            String correctAnswer = testQuestion.get(i).getCorrectOption();
            Long questionMark = testQuestion.get(i).getMark();
            maxMark = maxMark + questionMark;
            if(i < userAnswers.size() && correctAnswer.equals(userAnswers.get(i))){
                userMark = userMark + questionMark;
            }
        }
        TestResult testResult = TestResult.builder()
                .user(user)
                .test(testService.findById(answerModel.getTestId()))
                .total_mark(userMark)
                .maxMark(maxMark)
                .creationDate(LocalDate.now())
                .build();

        testResultRepository.save(testResult);
        return testResult;
    }

    @Override
    public TestResult findById(Long id) {
        return testResultRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Result with id \"" + id + "\" doesn't exist"));
    }

    @Override
    public List<TestResult> getAllTests(TestResult testResult) {
        try {
            return testResultRepository.findAll();
        } catch (NullPointerException ignored) {
            throw new ObjectNotFoundException("List is empty");
        }
    }

    @Override
    public List<TestResult> getAllTestsByUser(User user) {
        try {
            return testResultRepository.findAllByUser(user);
        } catch (NullPointerException ignored) {
            throw new ObjectNotFoundException("List is empty");
        }
    }

    @Override
    public List<TestResult> getAllTestsResults(Long id) {
        try {
            return testResultRepository.findAllByTestId(id);
        } catch (NullPointerException ignored) {
            throw new ObjectNotFoundException("List is empty");
        }
    }

    @Override
    public String deleteById(Long id) {
        TestResult testResult = findById(id);
        if (testResult == null) {
            throw new ObjectNotFoundException("Test result with id \"" + id + "\" doesn't exist");
        } else {
            testResultRepository.delete(testResult);
            return "Event with id \"" + id + "\" is deleted";
        }
    }
}
