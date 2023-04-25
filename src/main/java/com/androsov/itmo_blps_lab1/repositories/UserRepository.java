package com.androsov.itmo_blps_lab1.repositories;

import com.androsov.itmo_blps_lab1.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.*;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);

    User getByUsername(String username);
}
