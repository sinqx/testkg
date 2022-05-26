package ivt.tp.project2.controller;

import ivt.tp.project2.entity.TestResult;
import ivt.tp.project2.model.AnswerModel;
import ivt.tp.project2.service.TestResultService;
import ivt.tp.project2.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TestResultController {
    @Autowired
    private TestService testService;

    @Autowired
    private TestResultService testResultService;

    @GetMapping("/results")
    public String results(@ModelAttribute AnswerModel answer, Model model) {
        TestResult testResult = testResultService.findById((long)1);
        model.addAttribute("result", testResult);
        return "results";
    }
    @GetMapping("/scoreboard/{testId}")
    public String scoreboard(@PathVariable Long testId, Model model) {
        List<TestResult> testResult = testResultService.getAllTestsResults(testId);
        model.addAttribute("results", testResult);
        return "scoreboard";
    }

}
