package ivt.tp.project2.controller;

import ivt.tp.project2.entity.*;
import ivt.tp.project2.model.AnswerModel;
import ivt.tp.project2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Controller
//@RequestMapping("api/test/")
public class TestController {
    @Autowired
    private TestService testService;

    @Autowired
    private TestResultService testResultService;

    @Autowired
    private TestQuestionService testQuestionService;

    @Autowired
    private UserService userService;

    @Autowired
    private PhotoService photoService;

    @GetMapping("/createTest")
    public String createTest(Model model) {
        model.addAttribute("test", new Test());
        model.addAttribute("questions", new HashSet<TestQuestion>());
        return "/createTest";
    }


//    @PostMapping("/testSave")
//    public String save(@RequestParam(name = "file") MultipartFile multipartFile,  Model model, Test test) {
//        if(!multipartFile.isEmpty()) {
//            Photo photo = photoService.save(multipartFile);
//            test.setMainPhoto(photo);
//        }
//        model.addAttribute("test", test);
//        model.addAttribute("questions", new HashSet<TestQuestion>());
//        return "/questionAdd/";
//    }

    @PostMapping("/testSave")
    public String testSave(
            @RequestParam(name = "file") MultipartFile multipartFile,
            @Valid @ModelAttribute Test test,
            BindingResult bindingResult,
            Model model) {
        if(!multipartFile.isEmpty()) {
            Photo photo = photoService.save(multipartFile);
            test.setMainPhoto(photo);
        }
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByLogin(login);
        test.setCreatorUser(user);
        test.setCreationDate(LocalDateTime.now());
        if (bindingResult.hasErrors()) {
            return "testEdit";
        }

        testService.save(test);

        Iterable<Test> tests = testService.getAllTests();

        model.addAttribute("test", tests);

        return "redirect:/testDetails/" + test.getId();
    }

    @PostMapping("/questionAdd")
    public String questionAdd(
            @RequestParam Test test,
            @ModelAttribute TestQuestion testQuestion,
            Model model
    ) {
        testQuestion.setTest(test);

        testQuestionService.save(testQuestion);

        List<TestQuestion> questions = testQuestionService.findAllQuestionByTestId(test.getId());

        model.addAttribute("test", test);
        model.addAttribute("questions", questions);

        return "redirect:/testDetails/" + test.getId();
    }

    @GetMapping("/testDetails/{testId}")
    public String testDetails(
            @PathVariable Long testId,
            Model model) {
        Test test = testService.findById(testId);
        List<TestQuestion> questions = testQuestionService.findAllQuestionByTestId(test.getId());
        model.addAttribute("question", new TestQuestion());
        model.addAttribute("test", test);
        model.addAttribute("questions", questions);
        return "testDetails";
    }

    @GetMapping("/{testId}")
    public String findById(@PathVariable Long testId, Model model) {
        Test test = testService.findById(testId);
        model.addAttribute("testInfo", test);
        return "test";
    }

    @GetMapping("/allTests")
    public String getAllTests(Model model) {
        List<Test> tests = testService.getAllTests();
        model.addAttribute("testInfo", tests);
        return "/index";
    }

    @GetMapping("/getAllUserTests")
    public String getAllUserTests(Model model){
        User user = userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("tests", testService.findAllUserTests(user));
        return "getAllUserTests";
    }


    @GetMapping("/playTest/{testId}")
    public String playTest(@PathVariable Long testId, Model model) {
        SecurityContextHolder.getContext().getAuthentication().getName();
        Test test = testService.findById(testId);
        model.addAttribute("testInfo", test);
        model.addAttribute("answer", new AnswerModel());
        return "/playTest";
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute AnswerModel answer, Model model) {
        TestResult result = testResultService.calculateAndSave(answer);
        model.addAttribute("result", result);
        return "results";
    }

    @DeleteMapping("/{testId}")
    public void delete(@PathVariable Long testId) {
        testService.deleteById(testId);
    }
}
