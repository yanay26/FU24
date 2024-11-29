package com.example.fu24.try2.controller;

import com.example.fu24.try2.model.Transfer;
import com.example.fu24.try2.model.User;
import com.example.fu24.try2.service.CargoService;
import com.example.fu24.try2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;

@Controller
public class CargoController {

    @Autowired
    private CargoService service;
    @Autowired
    private UserService userService;

    @ModelAttribute("currentUser")
    public User getCurrentUser(Principal principal) {
        if (principal != null) {
            return userService.findByUsername(principal.getName());
        }
        return null;
    }

    // Обработчик для страницы /cargo
    @GetMapping("/cargo")
    public String viewHomePage(Model model, @Param("keyword") String keyword, @Param("sort") String sort, Principal principal) {
        List<Transfer> listTransfers;
        if ("new_to_old".equals(sort)) {
            listTransfers = service.listAll(keyword)
                    .stream()
                    .sorted(Comparator.comparing(Transfer::getDateto))
                    .toList();
        } else if ("old_to_new".equals(sort)) {
            listTransfers = service.listAll(keyword)
                    .stream()
                    .sorted(Comparator.comparing(Transfer::getDateto).reversed())
                    .toList();
        } else {
            listTransfers = service.listAll(keyword);
        }
        Transfer transfer = new Transfer();
        Transfer editTransfer = new Transfer();

        model.addAttribute("listTransfers", listTransfers);
        model.addAttribute("keyword", keyword);
        model.addAttribute("transfer", transfer);
        model.addAttribute("editTransfer", editTransfer);
        model.addAttribute("currentUser", getCurrentUser(principal));

        return "cargo"; // Возвращает представление cargo
    }

    // Обработчик для страницы /cargo/histogram
    @GetMapping("/cargo/histogram")
    public String viewChartPage(Model model, @Param("keyword") String keyword) {
        List<Transfer> listTransfers = service.listAll(keyword);
        model.addAttribute("listTransfers", listTransfers);
        return "cargo_histogram"; // Возвращает представление cargo_histogram
    }
}