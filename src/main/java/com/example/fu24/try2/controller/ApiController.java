package com.example.fu24.try2.controller;

import com.example.fu24.try2.model.Blog;
import com.example.fu24.try2.model.Transfer;
import com.example.fu24.try2.model.User;
import com.example.fu24.try2.service.BlogService;
import com.example.fu24.try2.service.CargoService;
import com.example.fu24.try2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private CargoService cargoService;
    @Autowired
    private UserService userService;

    @ModelAttribute("currentUser")
    public User getCurrentUser(Principal principal) {
        if (principal != null) {
            return userService.findByUsername(principal.getName());
        }
        return null;
    }

    public ApiController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping(value = "/auth/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        userService.registerUser(user, "ROLE_USER");
        return "redirect:/login";
    }



    @RequestMapping(value = "/admin/publish/save", method = RequestMethod.POST)
    public String saveBlog(@ModelAttribute("blog") Blog blog, Principal principal) {
        blog.setDate(LocalDateTime.now());
        User author = getCurrentUser(principal);
        blog.setAuthor(author);
        blogService.save(blog);
        return "redirect:/apub";
    }

    @RequestMapping(value = "/admin/publish/edit/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getBlogForEdit(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.get(id);
        if (blog == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", blog.getId());
        response.put("name", blog.getName());
        response.put("text", blog.getText());
        response.put("date", blog.getDate());
        response.put("author", blog.getAuthor() != null ? Map.of(
                "id", blog.getAuthor().getId(),
                "username", blog.getAuthor().getUsername()
        ) : null);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/admin/publish/update", method = RequestMethod.POST)
    public String updateBlog(@ModelAttribute("blog") Blog blog) {
        if (blog.getAuthor() != null && blog.getAuthor().getId() != null) {
            User existingUser = userService.getUserById(blog.getAuthor().getId());
            blog.setAuthor(existingUser);
        }
        blogService.save(blog);
        return "redirect:/apub";
    }

    @PostMapping("/admin/publish/delete")
    public @ResponseBody String deleteBlog(@RequestParam("id") Long id) {
        blogService.delete(id);
        return "Запись успешно удалена!";
    }




    @PostMapping("/admin/users/delete")
    public @ResponseBody String deleteUser(@RequestParam("id") Long id) {
        userService.delete(id);
        return "Пользователь успешно удален!";
    }

    @PostMapping("/admin/users/update_user/{id}")
    public String updateUserToAdmin(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            userService.updateUserRole(user.getUsername(), "ROLE_ADMIN");
        } else {
            throw new RuntimeException("User not found");
        }
        return "redirect:/padmin";
    }

    @PostMapping("/admin/users/update_admin/{id}")
    public String updateAdminToUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            userService.updateUserRole(user.getUsername(), "ROLE_USER");
        } else {
            throw new RuntimeException("User not found");
        }
        return "redirect:/padmin";
    }




    @PostMapping("/cargo/delete")
    public @ResponseBody String deleteTransfer(@RequestParam("id") Long id) {
        cargoService.delete(id);
        return "Груз успешно удален!";
    }

    @RequestMapping(value = "/cargo/edit/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getTransferForEdit(@PathVariable(name = "id") Long id) {
        Transfer transfer = cargoService.get(id);
        if (transfer == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", transfer.getId());
        response.put("name", transfer.getName());
        response.put("description", transfer.getDescription());
        response.put("cityfrom", transfer.getCityfrom());
        response.put("datefrom", transfer.getDatefrom());
        response.put("cityto", transfer.getCityto());
        response.put("dateto", transfer.getDateto());

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/cargo/save")
    public String saveTransfer(@ModelAttribute("transfer") Transfer transfer) {
       cargoService.save(transfer);
        return "redirect:/";
    }

}
