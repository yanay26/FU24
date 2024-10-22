package com.example.fu24;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Cargo {
    private Long id; // ID
    private String cargoname; // Название груза
    private String cargocontents; // Содержимое груза
    private String departurecity; // Город отправки
    private LocalDate departuredate; // Дата отправки
    private String arrivalcity; // Город прибытия
    private LocalDate arrivaldate; // Дата прибытия

    protected Cargo() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCargoname() {
        return cargoname;
    }

    public void setCargoname(String cargoname) {
        this.cargoname = cargoname;
    }

    public String getCargocontents() {
        return cargocontents;
    }

    public void setCargocontents(String cargocontents) {
        this.cargocontents = cargocontents;
    }

    public String getDeparturecity() {
        return departurecity;
    }

    public void setDeparturecity(String departurecity) {
        this.departurecity = departurecity;
    }

    public LocalDate getDeparturedate() {
        return departuredate;
    }

    public void setDeparturedate(LocalDate departuredate) {
        this.departuredate = departuredate;
    }

    public String getArrivalcity() {
        return arrivalcity;
    }

    public void setArrivalcity(String arrivalcity) {
        this.arrivalcity = arrivalcity;
    }

    public LocalDate getArrivaldate() {
        return arrivaldate;
    }

    public void setArrivaldate(LocalDate arrivaldate) {
        this.arrivaldate = arrivaldate;
    }

    @Override
    public String toString() {
        return "Cargo [id=" + id + ", cargoname=" + cargoname + ", cargocontents=" + cargocontents + ", departurecity=" + departurecity + ", departuredate=" + departuredate + ", arrivalcity=" + arrivalcity + ", arrivaldate=" + arrivaldate + "]";
    }
}


