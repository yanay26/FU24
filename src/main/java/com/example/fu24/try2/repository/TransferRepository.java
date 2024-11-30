package com.example.fu24.try2.repository;


import java.util.List;
import com.example.fu24.try2.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    @Query("SELECT p FROM Transfer p WHERE CONCAT(p.name, ' ', p.description, ' ', p.cityfrom, ' ', p.datefrom, ' ', p.cityto, ' ', p.dateto) LIKE %?1%")
    List<Transfer> search(String keyword);
}
