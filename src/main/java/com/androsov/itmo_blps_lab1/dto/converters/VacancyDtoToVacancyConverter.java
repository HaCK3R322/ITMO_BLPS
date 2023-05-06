package com.androsov.itmo_blps_lab1.dto.converters;

import com.androsov.itmo_blps_lab1.dto.VacancyDto;
import com.androsov.itmo_blps_lab1.entities.User;
import com.androsov.itmo_blps_lab1.entities.Vacancy;
import com.androsov.itmo_blps_lab1.repositories.UserRepository;
import com.androsov.itmo_blps_lab1.repositories.VacancyRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VacancyDtoToVacancyConverter implements Converter<VacancyDto, Vacancy> {
    private UserRepository userRepository;
    private VacancyRepository vacancyRepository;

    @Override
    public Vacancy convert(VacancyDto vacancyDto) {
        if(vacancyDto.getId() != null) {
            return vacancyRepository.findById(vacancyDto.getId()).orElseThrow(IllegalArgumentException::new);
        }

        Vacancy vacancy = new Vacancy();
        vacancy.setId(null);
        User user = userRepository.getByUsername(vacancyDto.getUsername());
        vacancy.setUser(user);
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setAddress(vacancyDto.getAddress());
        vacancy.setWorkExperience(vacancyDto.getWorkExperience());
        vacancy.setResponsibilities(vacancyDto.getResponsibilities());
        vacancy.setRequirements(vacancyDto.getRequirements());
        vacancy.setConditions(vacancyDto.getConditions());
        vacancy.setMinimumPayment(vacancyDto.getMinimumPayment());
        vacancy.setMaximumPayment(vacancyDto.getMaximumPayment());
        return vacancy;
    }
}
