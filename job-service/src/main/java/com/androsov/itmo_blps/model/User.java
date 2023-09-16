package com.androsov.itmo_blps.model;

import com.androsov.itmo_blps.model.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

// this saved in XmlFile, not in DB
// so basically this is not entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private Long id;

    private String username;

    private String password;

    private Role role;
}
