package com.androsov.itmo_blps.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new SimpleCORSFilter(), CorsFilter.class);


        http
                .cors()

                .and()
                .httpBasic()

                .and()
                .formLogin()

                .and()
                .authorizeRequests()
//                .antMatchers("/register", "/login").permitAll()
//                .antMatchers("/vacancy/search").permitAll()
                .antMatchers("/**").authenticated()
                .anyRequest().authenticated()

                .and()
                .csrf().disable();

        return http.build();
    }


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
////    @Bean
////    public DaoAuthenticationProvider daoAuthenticationProvider() {
////        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
////        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
////        daoAuthenticationProvider.setUserDetailsService(databaseUserDetailsService);
////        return daoAuthenticationProvider;
////    }

    @Bean
    public XmlFileAuthenticationProvider xmlFileAuthenticationProvider() {
        return new XmlFileAuthenticationProvider();
    }
}

