package com.example.fu24.try2.controller;

import com.example.fu24.try2.model.Blog;
import com.example.fu24.try2.model.User;
import com.example.fu24.try2.service.BlogService;
import com.example.fu24.try2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {



    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

    // Страница администратора с кнопками
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/main")
    public String viewAdminPage(Model model) {
        return "admin_page"; // Страница администратора
    }

    // Страница для публикации блогов (доступна только администратору)
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/publish")
    public String viewPublishPage(Model model, @Param("keyword") String keyword, Principal principal) {
        List<Blog> listBlog = blogService.listAll(keyword);
        Blog blog = new Blog();
        Blog editBlog = new Blog();
        List<User> authors = userService.listAll();

        model.addAttribute("authors", authors);
        model.addAttribute("listBlog", listBlog);
        model.addAttribute("keyword", keyword);
        model.addAttribute("blog", blog);
        model.addAttribute("editBlog", editBlog);
        model.addAttribute("currentUser", getCurrentUser(principal));

        return "admin_publish"; // Страница для администрирования публикаций
    }

    // Страница для управления пользователями (доступна только администратору)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/users")
    public String viewUsersPage(Model model) {
        List<User> listUsers = userService.listAll();
        model.addAttribute("listUsers", listUsers);
        return "admin_users"; // Страница для управления пользователями
    }

    // Получение текущего пользователя
    private User getCurrentUser(Principal principal) {
        if (principal != null) {
            return userService.findByUsername(principal.getName());
        }
        return null;
    }
}
