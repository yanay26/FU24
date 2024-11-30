package com.example.fu24.try2.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.fu24.try2.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    Role findByName(String roleName);
}
