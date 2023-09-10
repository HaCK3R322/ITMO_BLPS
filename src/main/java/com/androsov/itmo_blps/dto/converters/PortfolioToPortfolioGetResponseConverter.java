package com.androsov.itmo_blps.dto.converters;

import com.androsov.itmo_blps.dto.responses.PortfolioGetResponse;
import com.androsov.itmo_blps.entities.resume.Portfolio;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PortfolioToPortfolioGetResponseConverter implements Converter<Portfolio, PortfolioGetResponse> {
    @Override
    public PortfolioGetResponse convert(Portfolio portfolio) {
        PortfolioGetResponse response = new PortfolioGetResponse();
        response.setId(portfolio.getId());
        response.setResumeId(portfolio.getResume().getId());
        response.setDescription(portfolio.getDescription());
        response.setImageId(portfolio.getPortfolioImage().getId()); // TODO: rename image field in response to portfolio image
        return response;
    }
}
