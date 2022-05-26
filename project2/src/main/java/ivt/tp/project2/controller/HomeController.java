package ivt.tp.project2.controller;

import ivt.tp.project2.entity.Test;
import ivt.tp.project2.entity.User;
import ivt.tp.project2.service.TestService;
import ivt.tp.project2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private TestService testService;

    @GetMapping({"/home" , "/index", "/index.html", "/", ""})
    public String homePage(Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByLogin(login);
        List<Test> tests = testService.getAllTests();
        Collections.reverse(tests);
        model.addAttribute("tests", tests);
        model.addAttribute("index", user);
        return "home";
    }
}
