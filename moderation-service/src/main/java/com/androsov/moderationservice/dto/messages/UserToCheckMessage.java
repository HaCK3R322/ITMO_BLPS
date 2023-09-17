package com.androsov.moderationservice.dto.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserToCheckMessage {
    private Long userId;
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate dateOfBirth;
    private String city;
    private String number;
}
