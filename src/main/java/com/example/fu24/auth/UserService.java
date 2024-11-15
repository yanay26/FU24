package com.example.fu24.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User registerNewUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);  // Сохраняем пароль без шифрования

        Role userRole = roleRepository.findByName("USER");
        user.setRoles(Set.of(userRole));

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
