package com.androsov.itmo_blps_lab1.configuration;


import com.androsov.itmo_blps_lab1.controllers.dto.converters.ResumeDtoToResumeConverter;
import com.androsov.itmo_blps_lab1.controllers.dto.converters.ResumeToResumeDtoConverter;
import com.androsov.itmo_blps_lab1.model.entities.User;
import com.androsov.itmo_blps_lab1.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class ConverterConfig implements WebMvcConfigurer {
    UserRepository userRepository;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        ResumeToResumeDtoConverter resumeToResumeDtoConverter = new ResumeToResumeDtoConverter();
        ResumeDtoToResumeConverter resumeDtoToResumeConverter = new ResumeDtoToResumeConverter(userRepository);

        registry.addConverter(resumeDtoToResumeConverter);
        registry.addConverter(resumeToResumeDtoConverter);
    }
}
