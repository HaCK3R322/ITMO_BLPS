package com.androsov.itmo_blps.entities.resume;

import com.androsov.itmo_blps.entities.Image;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

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

    @NotBlank
    private String description;

    @OneToOne(fetch = FetchType.LAZY) // lazy fetch type to not carry this big ass image everywhere
    @JoinColumn(name = "portfolio_image_id")
    @Getter(AccessLevel.NONE) // for null-safety
    private Image portfolioImage;

    public Optional<Image> getPortfolioImage() {
        return Optional.ofNullable(portfolioImage);
    }


    public Portfolio() {
    }
}
