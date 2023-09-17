package com.androsov.moderationservice.services;

import com.androsov.moderationservice.dto.messages.UserToCheckMessage;
import com.androsov.moderationservice.model.entities.Criminal;
import com.androsov.moderationservice.model.entities.CriminalPhone;
import com.androsov.moderationservice.model.entities.SuspiciousUser;
import com.androsov.moderationservice.repositories.CriminalPhoneRepository;
import com.androsov.moderationservice.repositories.CriminalRepository;
import com.androsov.moderationservice.repositories.SuspiciousUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UserCheckService {
    private final CriminalRepository criminalRepository;
    private final CriminalPhoneRepository criminalPhoneRepository;
    private final SuspiciousUserRepository suspiciousUserRepository;

    public void checkUser(UserToCheckMessage user) {
        // suspicious if: ФИО + дата рождения, ИЛИ ФИО + город. + телефон

        SuspiciousUser suspiciousUser = new SuspiciousUser();
        suspiciousUser.setUserId(user.getUserId());

        criminalRepository.findByNameAndSurnameAndPatronymicAndDateOfBirth(
                user.getName(),
                user.getSurname(),
                user.getPatronymic(),
                user.getDateOfBirth()
        ).ifPresent(suspiciousUser::setAssociatedCriminal);

        criminalRepository.findByNameAndSurnameAndPatronymicAndCity(
                user.getName(),
                user.getSurname(),
                user.getPatronymic(),
                user.getCity()
        ).ifPresent(suspiciousUser::setAssociatedCriminal);

        Optional<CriminalPhone> criminalPhone = criminalPhoneRepository.findCriminalPhoneByNumber(
                user.getNumber()
        );
        criminalPhone.ifPresent(suspiciousUser::setCriminalPhone);

        if(suspiciousUser.getAssociatedCriminal() != null || suspiciousUser.getCriminalPhone() != null) {
            suspiciousUserRepository.save(suspiciousUser);
        }
    }
}
