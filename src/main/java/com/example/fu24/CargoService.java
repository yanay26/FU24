package com.example.fu24;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CargoService {
    @Autowired
    private CargoRepository repo;

    public List<Cargo> listAll(String keyword) {
        if (keyword != null) {
            return repo.search(keyword);
        }
        return repo.findAll();
    }

    public void save(Cargo cargo) {
        repo.save(cargo);
    }

    public Cargo get(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Map<LocalDate, Long> getCargosCountByDay() {
        List<Cargo> allCargos = repo.findAll(); // Получаем все грузы
        Map<LocalDate, Long> countMap = new HashMap<>();

        // Получаем текущую дату
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(13);

        for (Cargo cargo : allCargos) {
            LocalDate arrivalDate = cargo.getArrivaldate(); // Получаем дату прибытия груза

            // Учитываем только грузы за следующие 14 дней (текущая неделя + следующая)
            if (arrivalDate != null && (arrivalDate.isAfter(today.minusDays(1)) && arrivalDate.isBefore(endDate.plusDays(1)))) {
                countMap.put(arrivalDate, countMap.getOrDefault(arrivalDate, 0L) + 1);
            }
        }

        // Сортируем по дате в порядке возрастания
        return countMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}

