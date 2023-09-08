package com.androsov.itmo_blps.dto.converters;

import com.androsov.itmo_blps.dto.EducationDto;
import com.androsov.itmo_blps.dto.PortfolioDto;
import com.androsov.itmo_blps.entities.Image;
import com.androsov.itmo_blps.entities.resume.Education;
import com.androsov.itmo_blps.entities.resume.Portfolio;
import com.androsov.itmo_blps.entities.resume.Resume;
import com.androsov.itmo_blps.repositories.EducationRepository;
import com.androsov.itmo_blps.repositories.ImageRepository;
import com.androsov.itmo_blps.repositories.PortfolioRepository;
import com.androsov.itmo_blps.repositories.ResumeRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
@AllArgsConstructor
public class PortfolioDtoToPortfolioConverter implements Converter<PortfolioDto, Portfolio> {
    // TODO refactor and change repositories to services with search-safe methods (theyll throw noraml exceptions instead of that shit)

    PortfolioRepository portfolioRepository;
    ResumeRepository resumeRepository;
    ImageRepository imageRepository;

    @Override
    public Portfolio convert(PortfolioDto portfolioDto) throws EntityNotFoundException {
        if (portfolioDto.getId() != null) { // if we work with some created portfolio
            return portfolioRepository.findById(portfolioDto.getId()).orElseThrow(EntityNotFoundException::new);
        }

        Resume resume = resumeRepository.findById(portfolioDto.getResumeId()).orElseThrow(EntityNotFoundException::new);
        Image image = imageRepository.findById(portfolioDto.getImageId()).orElseThrow(EntityNotFoundException::new);

        Portfolio portfolio = new Portfolio();

        portfolio.setResume(resume);
        portfolio.setPortfolioImage(image);
        portfolio.setDescription(portfolioDto.getDescription());

        return portfolio;
    }

    public List<Portfolio> convert(List<PortfolioDto> portfolioDtos) {

        return portfolioDtos.stream()
                .map(this::convert)
                .toList();
    }
}