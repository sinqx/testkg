package ivt.tp.project2.controller;

import ivt.tp.project2.entity.Test;
import ivt.tp.project2.entity.TestQuestion;
import ivt.tp.project2.service.TestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
//@RequestMapping("")
public class TestQuestionController {
    @Autowired
    private TestQuestionService testQuestionService;

    @PostMapping("/newTestQuestionId")
    public String save(TestQuestion testQuestion) {
        testQuestionService.save(testQuestion);
        return "index";
    }

    @PostMapping("/newQuestion")
    public String questionAdd(@RequestParam Test test,
                              @ModelAttribute TestQuestion testQuestion,
                              Model model) {


        testQuestion.setTest(test);
        testQuestionService.save(testQuestion);
        List<TestQuestion> questions = testQuestionService.findAllQuestionByTestId(test.getId());

        model.addAttribute("Test", test);
        model.addAttribute("questions", questions);
        return "redirect:testDetails/" + test.getId();
    }

    @GetMapping("question/{testQuestionId}")
    public String findById(@PathVariable Long testQuestionId, Model model) {
        TestQuestion testQuestion = testQuestionService.findById(testQuestionId);
        model.addAttribute("testQuestionInfo", testQuestion);
        return "test";
    }

    @GetMapping("/allTestQuestions/")
    public String findAllQuestionById(Long id, Model model) {
        List<TestQuestion> testQuestion = testQuestionService.findAllQuestionByTestId((long)1);
        System.out.println(Arrays.toString(testQuestion.toArray()));
        return "index";
    }


    @GetMapping("/allTestAnswers/")
    public String findAllCorrectOptionByTestId(Long id, Model model) {
        List<TestQuestion> testQuestion = testQuestionService.findAllQuestionByTestId((long)1);
        System.out.println(Arrays.toString(testQuestion.toArray()));
        return "index";
    }
    @DeleteMapping("deleteQuestion/{testQuestionId}")
    public void delete(@PathVariable Long testQuestionId) {
        testQuestionService.deleteById(testQuestionId);
    }
}
