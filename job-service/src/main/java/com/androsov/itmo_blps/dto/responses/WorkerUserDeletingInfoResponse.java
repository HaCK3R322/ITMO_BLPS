package com.androsov.itmo_blps.dto.responses;

import com.androsov.itmo_blps.model.User;
import com.androsov.itmo_blps.model.entities.Image;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class WorkerUserDeletingInfoResponse {
    private User deletedUser;
    private List<Image> deletedImages;
    private List<EducationGetResponse> deletedEducations;
    private List<WorkExperienceGetResponse> deletedWorkExperiences;
    private List<PortfolioGetResponse> deletedPortfolios;
    private List<ResumeGetResponse> deletedResumes;
    private List<ResumeVacancyLinkGetResponse> deletedResumeVacancyLinks;
}
