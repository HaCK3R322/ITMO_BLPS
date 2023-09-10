package com.androsov.itmo_blps.repositories.implementations;

import com.androsov.itmo_blps.model.User;
import com.androsov.itmo_blps.model.entities.Role;
import com.androsov.itmo_blps.repositories.PrivilegeRepository;
import com.androsov.itmo_blps.repositories.RoleRepository;
import com.androsov.itmo_blps.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class XmlFileUserRepository implements UserRepository {
    RoleRepository roleRepository;

    @Override
    public User save(User user) {
        return user;
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return Optional.ofNullable(fastReturnUser((long)1, "worker1"));
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.ofNullable(fastReturnUser((long)1, "worker1"));
    }

    @Override
    public Optional<User> findById(Long userId) {
        return Optional.ofNullable(fastReturnUser((long)1, "worker1"));
    }

    private User fastReturnUser(Long userId, String username) {
        User user = new User();
        user.setId(userId);
        user.setUsername(username);
        user.setPassword("[PROTECTED]");

        Role role = roleRepository.findByName("ROLE_WORKER").get();
        user.setRole(role);

        return user;
    }
}
