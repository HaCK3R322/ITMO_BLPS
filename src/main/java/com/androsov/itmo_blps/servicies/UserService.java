package com.androsov.itmo_blps.servicies;

import com.androsov.itmo_blps.dto.requests.UserRegistrationRequest;
import com.androsov.itmo_blps.model.User;
import com.androsov.itmo_blps.model.entities.Role;
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
        if(userRepository.getByUsername(request.getUsername()).isPresent())
            throw new DataIntegrityViolationException("Username already exists");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));

        Optional<Role> roleOptional = roleRepository.findByName(request.getRoleName());

        if(roleOptional.isEmpty())
            throw new EntityNotFoundException("Role " + request.getRoleName() + " doesn't exists!");

        user.setRole(roleOptional.get());

        return userRepository.save(user);
    }

    public User getById(Long userId) throws EntityNotFoundException {
        Optional<User> optionalUser = userRepository.getById(userId);

        if(optionalUser.isEmpty())
            throw new EntityNotFoundException("User with id " + userId + " not found!");

        return optionalUser.get();
    }

    public User getByUsername(String username) throws EntityNotFoundException {
        Optional<User> optionalUser = userRepository.getByUsername(username);

        if(optionalUser.isEmpty())
            throw new EntityNotFoundException("User with username " + username + " not found!");

        return optionalUser.get();
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }
}
