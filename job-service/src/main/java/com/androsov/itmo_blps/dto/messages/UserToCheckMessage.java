package com.androsov.itmo_blps.dto.messages;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserToCheckMessage {
    private Long userId;
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate dateOfBirth;
    private String city;
}
