package com.androsov.itmo_blps.repositories;

import com.androsov.itmo_blps.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);

    User getByUsername(String username);

    Boolean existsByUsername(String username);

    boolean existsById(Long id);

    User findById(Long userId);
}
