package com.androsov.itmo_blps.servicies;

import com.androsov.itmo_blps.dto.requests.UserRegistrationRequest;
import com.androsov.itmo_blps.entities.Role;
import com.androsov.itmo_blps.entities.User;
import com.androsov.itmo_blps.repositories.RoleRepository;
import com.androsov.itmo_blps.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public User registerFromRequest(UserRegistrationRequest request) {
        // if username not unique throw exception
        if (userRepository.findByUsername(request.getUsername().trim()) != null) {
            throw new DataIntegrityViolationException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));

        Optional<Role> roleOptional = roleRepository.findByName(request.getRoleName());

        if(roleOptional.isEmpty())
            throw new EntityNotFoundException("Role " + request.getRoleName() + " doesn't exists!");

        user.setRole(roleOptional.get());

        return userRepository.save(user);
    }

    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }
}
