package com.example.fu24.try2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.fu24.try2.model.User;
import com.example.fu24.try2.service.UserService;

@Controller
public class AuthController {
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    private final UserService userService;

    @GetMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }
}
