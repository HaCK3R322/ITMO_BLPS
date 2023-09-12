package com.androsov.itmo_blps.dto.responses;

import com.androsov.itmo_blps.model.User;
import com.androsov.itmo_blps.model.entities.Image;
import com.androsov.itmo_blps.model.entities.resume.Education;
import com.androsov.itmo_blps.model.entities.resume.Portfolio;
import com.androsov.itmo_blps.model.entities.resume.Resume;
import com.androsov.itmo_blps.model.entities.resume.WorkExperience;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class UserDeletingInfoResponse {
    private User deletedUser;
    private List<Image> deletedImages;
    private List<EducationGetResponse> deletedEducations;
    private List<WorkExperienceGetResponse> deletedWorkExperiences;
    private List<PortfolioGetResponse> deletedPortfolios;
    private List<ResumeGetResponse> deletedResumes;
    private List<ResumeVacancyLinkGetResponse> deletedResumeVacancyLinks;
}
