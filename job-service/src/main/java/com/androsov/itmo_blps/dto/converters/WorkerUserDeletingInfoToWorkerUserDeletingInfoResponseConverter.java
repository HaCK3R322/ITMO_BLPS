package com.androsov.itmo_blps.dto.converters;

import com.androsov.itmo_blps.dto.responses.*;
import com.androsov.itmo_blps.model.WorkerUserDeletingInfo;
import com.androsov.itmo_blps.model.entities.ResumeVacancyLink;
import com.androsov.itmo_blps.model.entities.resume.Education;
import com.androsov.itmo_blps.model.entities.resume.Portfolio;
import com.androsov.itmo_blps.model.entities.resume.Resume;
import com.androsov.itmo_blps.model.entities.resume.WorkExperience;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class WorkerUserDeletingInfoToWorkerUserDeletingInfoResponseConverter
        implements Converter<WorkerUserDeletingInfo, WorkerUserDeletingInfoResponse> {
    private final Converter<Education, EducationGetResponse> educationConverter;
    private final Converter<WorkExperience, WorkExperienceGetResponse> workExperienceConverter;
    private final Converter<Portfolio, PortfolioGetResponse> portfolioConverter;
    private final Converter<Resume, ResumeGetResponse> resumeConverter;
    private final Converter<ResumeVacancyLink, ResumeVacancyLinkGetResponse> resumeVacancyLinkConverter;

    @Override
    public WorkerUserDeletingInfoResponse convert(WorkerUserDeletingInfo source) {
        WorkerUserDeletingInfoResponse workerUserDeletingInfoResponse = new WorkerUserDeletingInfoResponse();

        workerUserDeletingInfoResponse.setDeletedUser(source.getDeletedUser());
        workerUserDeletingInfoResponse.setDeletedImages(source.getDeletedImages());

        List<EducationGetResponse> educationsResponse = source.getDeletedEducations().stream()
                .map(educationConverter::convert)
                .toList();
        workerUserDeletingInfoResponse.setDeletedEducations(educationsResponse);

        List<WorkExperienceGetResponse> workExperienceResponses = source.getDeletedWorkExperiences().stream()
                .map(workExperienceConverter::convert)
                .toList();
        workerUserDeletingInfoResponse.setDeletedWorkExperiences(workExperienceResponses);

        List<PortfolioGetResponse> portfolioResponses = source.getDeletedPortfolios().stream()
                .map(portfolioConverter::convert)
                .toList();
        workerUserDeletingInfoResponse.setDeletedPortfolios(portfolioResponses);

        List<ResumeGetResponse> resumeGetResponseList = source.getDeletedResumes().stream()
                .map(resumeConverter::convert)
                .toList();
        workerUserDeletingInfoResponse.setDeletedResumes(resumeGetResponseList);

        List<ResumeVacancyLinkGetResponse> resumeVacancyLinkGetResponses = source.getDeletedResumeVacancyLinks().stream()
                .map(resumeVacancyLinkConverter::convert)
                .toList();
        workerUserDeletingInfoResponse.setDeletedResumeVacancyLinks(resumeVacancyLinkGetResponses);

        return workerUserDeletingInfoResponse;
    }
}
