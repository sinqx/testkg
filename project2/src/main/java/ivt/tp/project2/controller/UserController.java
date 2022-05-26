package ivt.tp.project2.controller;

import  ivt.tp.project2.entity.Photo;
import ivt.tp.project2.entity.Test;
import  ivt.tp.project2.entity.User;
import  ivt.tp.project2.service.PhotoService;
import ivt.tp.project2.service.TestService;
import  ivt.tp.project2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
//@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private TestService testService;

    @GetMapping("/getAllUsers")
    public ResponseEntity getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity getById(@PathVariable Long userId) {
        try {
            User user = userService.findById(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("/get-name")
    public String getName(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/userSettings")
    public String pageSetting(Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByLogin(login);
        Photo photo = photoService.findById(user.getPhotoId());
        model.addAttribute("Photo", photo);
        model.addAttribute("user", user);
        return "userSettings";
    }

    @PostMapping("/saveSettings")
    public String settingSave(@RequestParam(name = "file") MultipartFile multipartFile, User user) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User userChange = userService.findByLogin(login);
        if(!multipartFile.isEmpty()) {
            Photo photo = photoService.save(multipartFile);
            userChange.setPhotoId(photo.getId());
        }
        userChange.setFullName(user.getFullName());
        userChange.setDateOfBirth(user.getDateOfBirth());
        userChange.setPhoneNumber(user.getPhoneNumber());
        userService.save(userChange);
        return "redirect:userPage";
    }


    @GetMapping("/userPage")
    public String OpenMyPage(Model model) {
            String login = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByLogin(login);
            Photo photo = photoService.findById(user.getPhotoId());
            List<Test> tests = testService.findAllUserTests(user);
            model.addAttribute("Photo", photo);
            model.addAttribute("myPage", user);
            model.addAttribute("tests", tests);
            return "userPage";
    }

    @GetMapping("/userPage/{userId}")
    public String userPage(@PathVariable long userId, Model model) {
        User user = userService.findById(userId);
        List<Test> tests = testService.findAllUserTests(user);
        Photo photo = photoService.findById(user.getPhotoId());
        model.addAttribute("Photo", photo);
        model.addAttribute("myPage", user);
        model.addAttribute("tests", tests);
        return "userPage";
    }



    @DeleteMapping("/{userId}")
    public ResponseEntity deleteById(@PathVariable Long userId) {
        try {
            String answer = userService.deleteById(userId);
            return new ResponseEntity<>(answer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity findUsersByPartOfFullName(@PathVariable String name) {
        try {
            List<User> users = userService.findUsersByPartOfFullName(name);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @PostMapping("/changeStatus/{userId}")
    public ResponseEntity changeStatusById(@PathVariable Long userId) {
        try {
            User user = userService.changeStatusById(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }
}
