package com.androsov.itmo_blps_lab1.servicies;

import com.androsov.itmo_blps_lab1.model.entities.User;
import com.androsov.itmo_blps_lab1.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User register(User user) {
        // if username not unique throw exception
        if (userRepository.findByUsername(user.getUsername().trim()) != null) {
            throw new DataIntegrityViolationException("Username already exists");
        }

        return save(user);
    }

    public User save(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

}
