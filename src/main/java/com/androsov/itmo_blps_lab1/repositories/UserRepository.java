package com.androsov.itmo_blps_lab1.repositories;

import com.androsov.itmo_blps_lab1.model.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);

    User getByUsername(String username);

    Boolean existsByUsername(String username);

    boolean existsById(Long id);
}
