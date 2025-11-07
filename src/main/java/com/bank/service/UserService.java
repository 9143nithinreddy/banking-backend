package com.bank.service;

import com.bank.model.Role;
import com.bank.model.User;
import com.bank.repository.UserRepository;
import com.bank.util.JwtUtil; // if you have a separate JWT utility class
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;  // make sure you have a Bean defined

    @Autowired(required = false)
    private JwtUtil jwtUtil; // your class for token generation

    // ✅ REGISTER USER
    public User register(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists!");
        }

        // encode password before savinga
        if (passwordEncoder != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    // ✅ LOGIN METHOD
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("Invalid username or password");
        }

        boolean matches = (passwordEncoder == null)
                ? user.getPassword().equals(password)
                : passwordEncoder.matches(password, user.getPassword());

        if (!matches) {
            throw new RuntimeException("Invalid username or password");
        }

        // ✅ Ensure jwtUtil exists and return a proper JWT
        if (jwtUtil == null) {
            throw new RuntimeException("JWT Utility not initialized!");
        }

        return jwtUtil.generateToken(user.getUsername(), user.getRole().name());
    }


    // ✅ ASSIGN ROLE
    public User assignRole(Long userId, Role role) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        User user = optionalUser.get();
        user.setRole(role);
        return userRepository.save(user);
    }
}
