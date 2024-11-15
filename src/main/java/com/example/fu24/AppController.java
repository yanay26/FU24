package com.example.fu24;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

import com.example.fu24.auth.User;
import com.example.fu24.auth.UserRepository;
import com.example.fu24.auth.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

    @Autowired
    private CargoService service;
    @Autowired
    private UserService userService;  // Добавляем репозиторий для пользователей

    @RequestMapping("/")
    public String viewHomePage(Model model, @Param("keyword") String keyword) {
        List<Cargo> listCargo = service.listAll(keyword);
        model.addAttribute("listCargo", listCargo);
        model.addAttribute("keyword", keyword);

        // Получаем количество грузов по дням и преобразуем в список
        Map<LocalDate, Long> cargosByDayMap = service.getCargosCountByDay();
        List<Map.Entry<LocalDate, Long>> cargosByDayList = new ArrayList<>(cargosByDayMap.entrySet());

        model.addAttribute("cargosByDayList", cargosByDayList);

        return "index"; // Главная страница
    }

    @RequestMapping("/new")
    public String showNewCargoForm(Model model) {
        Cargo cargo = new Cargo();
        model.addAttribute("cargo", cargo);
        return "new_cargo"; // Форма для добавления груза
    }
    @GetMapping("/login")
    public String login(Model model) {
        // Это необходимо для отображения ошибки аутентификации
        return "login"; // возораздает имя шаблона
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveCargo(@ModelAttribute("cargo") Cargo cargo) {
        service.save(cargo);
        return "redirect:/"; // Перенаправление на главную страницу после сохранения
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditCargoForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_cargo"); // Страница редактирования
        Cargo cargo = service.get(id);
        mav.addObject("cargo", cargo);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteCargo(@PathVariable(name = "id") Long id) {
        service.delete(id);
        return "redirect:/"; // Перенаправление на главную страницу после удаления
    }

    @GetMapping("/histogram/data")
    @ResponseBody
    public ResponseEntity<Map<LocalDate, Long>> getCargosCountByDay() {
        Map<LocalDate, Long> cargosCount = service.getCargosCountByDay();
        return ResponseEntity.ok(cargosCount);
    }

    @GetMapping("/histogram")
    public String showHistogram(Model model) {
        // Получаем данные для гистограммы
        Map<LocalDate, Long> cargosCount = service.getCargosCountByDay();
        model.addAttribute("cargosCount", cargosCount);
        return "histogram"; // Страница с гистограммой
    }

//    @GetMapping("/admin")
//    public String adminPage(Model model, Principal principal) {
//        // Проверка роли администратора
//        User user = userRepository.findByUsername(principal.getName()).orElse(null);
//        if (user == null || user.getRoles().stream().noneMatch(role -> role.getName().equals("ADMIN"))) {
//            return "redirect:/access-denied"; // перенаправление на страницу отказа в доступе
//        }
//
//        model.addAttribute("users", userRepository.findAll());
//        return "admin"; // Возвращаем шаблон admin.html
//    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPage(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName(); // Получаем имя текущего аутентифицированного пользователя
            model.addAttribute("username", username);
        }
        // Здесь вы можете добавить другие данные в модель, если нужно
        return "admin"; // возвращает имя вашего шаблона admin.html
    }
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied"; // Страница с сообщением об отказе в доступе
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login?logout=true"; // Перенаправление на страницу входа
    }

}

