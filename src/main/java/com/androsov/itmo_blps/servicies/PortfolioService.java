package com.androsov.itmo_blps.servicies;

import com.androsov.itmo_blps.dto.requests.PortfolioCreateRequest;
import com.androsov.itmo_blps.entities.resume.Portfolio;
import com.androsov.itmo_blps.entities.resume.Resume;
import com.androsov.itmo_blps.repositories.PortfolioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class PortfolioService {
    private PortfolioRepository portfolioRepository;
    private ImageService imageService;

    public Portfolio createFromRequest(Resume resume, PortfolioCreateRequest request)
            throws EntityNotFoundException, AccessDeniedException {
        Portfolio portfolio = new Portfolio();

        portfolio.setResume(resume);
        portfolio.setPortfolioImage(imageService.getById(request.getImageId()));
        portfolio.setDescription(request.getDescription());

        return portfolioRepository.save(portfolio);
    }

    public List<Portfolio> getAllByResume(Resume resume) {
        return portfolioRepository.findAllByResume(resume);
    }
}

