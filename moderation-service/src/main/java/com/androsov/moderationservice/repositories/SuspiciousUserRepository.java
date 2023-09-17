package com.androsov.moderationservice.repositories;

import com.androsov.moderationservice.model.entities.SuspiciousUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuspiciousUserRepository extends JpaRepository<SuspiciousUser, Long> {
}
