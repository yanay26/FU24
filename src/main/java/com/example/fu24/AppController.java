package com.example.fu24;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

    @Autowired
    private CargoService service;

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
}

