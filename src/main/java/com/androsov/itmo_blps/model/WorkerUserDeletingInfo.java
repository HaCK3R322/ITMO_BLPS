package com.androsov.itmo_blps.model;

import com.androsov.itmo_blps.model.entities.Image;
import com.androsov.itmo_blps.model.entities.ResumeVacancyLink;
import com.androsov.itmo_blps.model.entities.resume.Education;
import com.androsov.itmo_blps.model.entities.resume.Portfolio;
import com.androsov.itmo_blps.model.entities.resume.Resume;
import com.androsov.itmo_blps.model.entities.resume.WorkExperience;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class WorkerUserDeletingInfo {
     private User deletedUser;
     private List<Image> deletedImages;
     private List<Education> deletedEducations;
     private List<WorkExperience> deletedWorkExperiences;
     private List<Portfolio> deletedPortfolios;
     private List<Resume> deletedResumes;
     private List<ResumeVacancyLink> deletedResumeVacancyLinks;
}
