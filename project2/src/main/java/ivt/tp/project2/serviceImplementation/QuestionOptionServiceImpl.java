package ivt.tp.project2.serviceImplementation;

import ivt.tp.project2.entity.QuestionOption;
import ivt.tp.project2.exception.ObjectNotFoundException;
import ivt.tp.project2.repository.QuestionOptionRepository;
import ivt.tp.project2.service.QuestionOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionOptionServiceImpl implements QuestionOptionService {

    @Autowired
    private QuestionOptionRepository questionOptionRepository;

    @Override
    public QuestionOption save(QuestionOption questionOption) {
        return questionOptionRepository.save(questionOption);
    }

    @Override
    public QuestionOption findById(Long id) {
        return questionOptionRepository.findById(id).
                orElseThrow(() -> new ObjectNotFoundException("QuestionOption with id \"" + id + "\" doesn't exist"));
    }

    @Override
    public List<QuestionOption> getAllQuestionOptions() {
        return questionOptionRepository.findAll();
    }

    @Override
    public String deleteById(Long id) {
        QuestionOption questionOption = findById(id);
        if (questionOption == null) {
            throw new ObjectNotFoundException("Question Option with id \"" + id + "\" doesn't exist");
        } else {
            return "Question Option with id \"" + id + "\" is deleted";
        }
    }
}
