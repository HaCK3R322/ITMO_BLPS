package com.androsov.itmo_blps.dto.converters;

import com.androsov.itmo_blps.dto.responses.ResumeGetResponse;
import com.androsov.itmo_blps.dto.responses.ResumeVacancyLinkGetResponse;
import com.androsov.itmo_blps.entities.Image;
import com.androsov.itmo_blps.entities.ResumeVacancyLink;
import com.androsov.itmo_blps.entities.resume.Resume;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ResumeVacancyLinkToCreateResumeVacancyLinkResponseConverter implements Converter<ResumeVacancyLink, ResumeVacancyLinkGetResponse> {
    @Override
    public ResumeVacancyLinkGetResponse convert(ResumeVacancyLink source) {
        ResumeVacancyLinkGetResponse response = new ResumeVacancyLinkGetResponse();

        response.setId(source.getId());
        response.setResumeId(source.getResume().getId());
        response.setVacancyId(source.getVacancy().getId());

        return response;
    }
}
