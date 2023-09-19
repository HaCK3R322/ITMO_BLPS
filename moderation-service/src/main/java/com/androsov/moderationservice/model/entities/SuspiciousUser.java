package com.androsov.moderationservice.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "suspicious_users")
public class SuspiciousUser {
    @Id
    private Long userId;

    @OneToOne
    @JoinColumn(name = "associated_criminal_id")
    private Criminal associatedCriminal;

    @OneToOne
    @JoinColumn(name = "criminal_phone_id")
    private CriminalPhone criminalPhone;
}
