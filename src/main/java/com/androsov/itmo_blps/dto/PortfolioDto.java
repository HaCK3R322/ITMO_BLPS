package com.androsov.itmo_blps.dto;

import com.androsov.itmo_blps.entities.Image;
import com.androsov.itmo_blps.entities.resume.Resume;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioDto {

    @JsonProperty("id")
    @NotNull
    private Long id;

    @JsonProperty("resumeId")
    @NotNull
    private Long resumeId;

    @JsonProperty("imageId")
    @NotNull
    private Long imageId;

    @NotBlank
    private String description;

}
