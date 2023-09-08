package com.androsov.itmo_blps.dto.converters;

import com.androsov.itmo_blps.dto.EducationDto;
import com.androsov.itmo_blps.dto.PortfolioDto;
import com.androsov.itmo_blps.entities.resume.Education;
import com.androsov.itmo_blps.entities.resume.Portfolio;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class PortfolioToPortfolioDtoConverter implements Converter<Portfolio, PortfolioDto> {
    @Override
    public PortfolioDto convert(Portfolio portfolio) {
        PortfolioDto portfolioDto = new PortfolioDto();

        portfolioDto.setId(portfolio.getId());
        portfolioDto.setDescription(portfolio.getDescription());
        portfolioDto.setImageId(portfolio.getPortfolioImage().getId());
        portfolioDto.setResumeId(portfolio.getResume().getId());

        return portfolioDto;
    }

    public List<PortfolioDto> convert(List<Portfolio> portfolios) {
        return portfolios.stream()
                .map(this::convert)
                .toList();
    }
}

