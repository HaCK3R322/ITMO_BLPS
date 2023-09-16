package com.androsov.itmo_blps.model.entities;

import com.androsov.itmo_blps.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "data")
    private byte[] data;

    @Column(name = "user_id")
    private Long userId;

    public Image() {

    }
}
