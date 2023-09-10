package com.androsov.itmo_blps.repositories;

import com.androsov.itmo_blps.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {}

