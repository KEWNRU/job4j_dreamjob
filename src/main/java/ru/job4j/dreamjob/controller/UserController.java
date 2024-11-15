package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.repository.Sql2oUserRepository;
import ru.job4j.dreamjob.service.UserService;

@ThreadSafe
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute User user) {
        try {
          userService.save(user);
        } catch (Sql2oUserRepository.DuplicateKeyException e) {
            model.addAttribute("message", e.getMessage());
            e.printStackTrace();
            return "errors/404";
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "/users/register";
    }
}
