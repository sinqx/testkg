package ivt.tp.project2.controller;

import  ivt.tp.project2.entity.User;
import  ivt.tp.project2.model.AuthModel;
import  ivt.tp.project2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
//@RequestMapping("/api/auth")
public class AuthAndRegistrationController {
    @Autowired
    private UserService userService;


    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/sign-up")
    public String save(User user) {
        try {
            userService.saveWithPasswordEncode(user);
           // return new ResponseEntity<>("   ", HttpStatus.OK);
            return "redirect:/login";
        } catch (Exception e) {
          //  return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
            return "error";
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity getToken(AuthModel authModel) {
        try {
            String token = userService.getTokenByAuthModel(authModel);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }
}
