package com.androsov.itmo_blps.configuration.security;

import com.androsov.itmo_blps.servicies.DatabaseUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

class SimpleCORSFilter extends OncePerRequestFilter {

    protected static final String ALLOWED_ORIGINS = "*";

    protected static final String ALLOWED_METHODS = "GET,POST,PUT,DELETE,OPTIONS,UPDATE";
    protected static final String ALLOWED_HEADERS = "X-Requested-With,Content-Type,Accept,Origin,Authorization,X-CSRF-TOKEN";
    protected static final String ALLOW_CREDENTIALS = "true";
    protected static final String MAX_AGE = "604800";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, javax.servlet.FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", ALLOWED_ORIGINS);
        response.setHeader("Access-Control-Allow-Methods", ALLOWED_METHODS);
        response.setHeader("Access-Control-Max-Age", MAX_AGE);
        response.setHeader("Access-Control-Allow-Headers", ALLOWED_HEADERS);
        response.setHeader("Access-Control-Allow-Credentials", ALLOW_CREDENTIALS);

        filterChain.doFilter(request, response);
    }
}

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final DatabaseUserDetailsService databaseUserDetailsService;

    public SecurityConfiguration(DatabaseUserDetailsService databaseUserDetailsService) {
        this.databaseUserDetailsService = databaseUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()

                .and()
                .authorizeRequests()
                .antMatchers("/register", "/login").permitAll()
                .antMatchers("/vacancy/search").permitAll()
                .antMatchers("/**").authenticated()
                .anyRequest().authenticated()

                .and()
                .rememberMe()
                .key("uniqueAndSecret")
                .and()
                .csrf().disable();

        http
                .logout(logout -> logout
                        .permitAll()
                        .logoutSuccessHandler((request, response, authentication) -> response.setStatus(HttpStatus.OK.value())
                        )
                );

        http
                .addFilterBefore(new SimpleCORSFilter(), CorsFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(databaseUserDetailsService);
        return daoAuthenticationProvider;
    }
}

