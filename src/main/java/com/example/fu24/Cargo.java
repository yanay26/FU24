package com.example.fu24;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Getter
@Setter
@Entity
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID
    private String cargoname; // Название груза
    private String cargocontents; // Содержимое груза
    private String departurecity; // Город отправки
    private LocalDate departuredate; // Дата отправки
    private String arrivalcity; // Город прибытия
    private LocalDate arrivaldate; // Дата прибытия

    protected Cargo() {
    }
    @Override
    public String toString() {
        return "Cargo [id=" + id + ", cargoname=" + cargoname + ", cargocontents=" + cargocontents + ", departurecity=" + departurecity + ", departuredate=" + departuredate + ", arrivalcity=" + arrivalcity + ", arrivaldate=" + arrivaldate + "]";
    }
}


