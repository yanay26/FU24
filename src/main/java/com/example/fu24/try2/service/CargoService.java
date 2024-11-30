package com.example.fu24.try2.service;


import java.util.List;
import com.example.fu24.try2.model.Transfer;
import com.example.fu24.try2.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CargoService {
    @Autowired
    private TransferRepository repo;

    public List<Transfer> listAll(String keyword) {
        if (keyword != null) {
            return repo.search(keyword);
        }
        return repo.findAll();
    }

    public void save(Transfer transfer) {
        repo.save(transfer);
    }

    public Transfer get(Long id) {
        return repo.findById(id).get();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}