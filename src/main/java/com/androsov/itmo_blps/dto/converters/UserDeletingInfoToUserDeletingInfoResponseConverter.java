package com.androsov.itmo_blps.dto.converters;

import com.androsov.itmo_blps.dto.responses.*;
import com.androsov.itmo_blps.model.UserDeletingInfo;
import com.androsov.itmo_blps.model.entities.ResumeVacancyLink;
import com.androsov.itmo_blps.model.entities.resume.Education;
import com.androsov.itmo_blps.model.entities.resume.Portfolio;
import com.androsov.itmo_blps.model.entities.resume.Resume;
import com.androsov.itmo_blps.model.entities.resume.WorkExperience;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserDeletingInfoToUserDeletingInfoResponseConverter
        implements Converter<UserDeletingInfo, UserDeletingInfoResponse> {

    private final Converter<Education, EducationGetResponse> educationConverter;
    private final Converter<WorkExperience, WorkExperienceGetResponse> workExperienceConverter;
    private final Converter<Portfolio, PortfolioGetResponse> portfolioConverter;
    private final Converter<Resume, ResumeGetResponse> resumeConverter;
    private final Converter<ResumeVacancyLink, ResumeVacancyLinkGetResponse> resumeVacancyLinkConverter;

    @Override
    public UserDeletingInfoResponse convert(UserDeletingInfo source) {
        UserDeletingInfoResponse userDeletingInfoResponse = new UserDeletingInfoResponse();

        userDeletingInfoResponse.setDeletedUser(source.getDeletedUser());
        userDeletingInfoResponse.setDeletedImages(source.getDeletedImages());

        List<EducationGetResponse> educationsResponse = source.getDeletedEducations().stream()
                .map(educationConverter::convert)
                .toList();
        userDeletingInfoResponse.setDeletedEducations(educationsResponse);

        List<WorkExperienceGetResponse> workExperienceResponses = source.getDeletedWorkExperiences().stream()
                .map(workExperienceConverter::convert)
                .toList();
        userDeletingInfoResponse.setDeletedWorkExperiences(workExperienceResponses);

        List<PortfolioGetResponse> portfolioResponses = source.getDeletedPortfolios().stream()
                .map(portfolioConverter::convert)
                .toList();
        userDeletingInfoResponse.setDeletedPortfolios(portfolioResponses);

        List<ResumeGetResponse> resumeGetResponseList = source.getDeletedResumes().stream()
                .map(resumeConverter::convert)
                .toList();
        userDeletingInfoResponse.setDeletedResumes(resumeGetResponseList);

        List<ResumeVacancyLinkGetResponse> resumeVacancyLinkGetResponses = source.getDeletedResumeVacancyLinks().stream()
                .map(resumeVacancyLinkConverter::convert)
                .toList();
        userDeletingInfoResponse.setDeletedResumeVacancyLinks(resumeVacancyLinkGetResponses);

        return userDeletingInfoResponse;
    }
}
