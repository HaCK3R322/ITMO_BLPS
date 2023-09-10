package com.androsov.itmo_blps.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ConverterConfiguration implements WebMvcConfigurer {
    private final List<Converter<?, ?>> converters; // spring creates it auto

    @Override
    public void addFormatters(FormatterRegistry registry) {
        converters.forEach(registry::addConverter);
    }
}