package com.androsov.itmo_blps.repositories;

import com.androsov.itmo_blps.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    User save(User user);

    Optional<User> getByUsername(String username);
    Optional<User> getById(Long id);

    User deleteById(Long userId);
}
