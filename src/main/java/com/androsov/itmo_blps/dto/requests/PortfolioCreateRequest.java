package com.androsov.itmo_blps.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioCreateRequest {

    @JsonProperty("imageId")
    @NotNull
    private Long imageId;

    @NotBlank
    private String description;

}