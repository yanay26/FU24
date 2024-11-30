package com.example.fu24.try2.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.fu24.try2.model.Blog;
import com.example.fu24.try2.model.User;
import com.example.fu24.try2.service.BlogService;
import com.example.fu24.try2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BlogController {
    @Autowired
    private BlogService service;
    @Autowired
    private UserService userService;

    @ModelAttribute("currentUser")
    public User getCurrentUser(Principal principal) {
        if (principal != null) {
            return userService.findByUsername(principal.getName());
        }
        return null;
    }

    @GetMapping("/autoblog")
    public String viewBlogPage(Model model, @RequestParam(required = false, defaultValue = "") String name,
                                           @RequestParam(required = false, defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                                           @RequestParam(required = false, defaultValue = "") String text) {
        List<Blog> listBlog;
        if ((name != null && !name.isEmpty()) || (text != null && !text.isEmpty()) || date != null) {
            listBlog = service.search(name, text, date);
        } else {
            listBlog = service.listAllWithoutKeyWord();
        }

        model.addAttribute("listBlog", listBlog);
        model.addAttribute("name", name);
        model.addAttribute("date", date);
        model.addAttribute("text", text);

        return "user_autoblog";
    }
}
