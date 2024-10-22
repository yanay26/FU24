package com.example.fu24;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
    @Query("SELECT c FROM Cargo c WHERE CONCAT(c.cargoname, ' ', c.cargocontents, ' ', c.departurecity, ' ', c.departuredate, ' ', c.arrivalcity, ' ', c.arrivaldate) LIKE %?1%")
    List<Cargo> search(String keyword);
}

