package com.androsov.itmo_blps_lab1.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;


//TODO: make all values NotNull (create validators)

@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username", unique = true)
    @Size(min = 3, max = 32, message = "Username should be in range of 3-32 symbols")
    private String username;

    @Column(name = "password")
    private String password;

    protected User() {}
}
