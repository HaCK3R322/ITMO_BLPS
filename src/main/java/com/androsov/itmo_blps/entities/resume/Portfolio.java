package com.androsov.itmo_blps.entities.resume;

import com.androsov.itmo_blps.entities.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    private String description;

    @OneToOne(fetch = FetchType.LAZY) // lazy fetch type to not carry this big ass image everywhere
    @JoinColumn(name = "portfolio_image_id")
    private Image portfolioImage;

    public Portfolio() {
    }
}
