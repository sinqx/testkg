package ivt.tp.project2.service;

import ivt.tp.project2.entity.QuestionOption;

import java.util.List;

public interface QuestionOptionService {
    QuestionOption save(QuestionOption questionOption);
    QuestionOption findById(Long id);
    List<QuestionOption> getAllQuestionOptions();
    String deleteById(Long id);
}
