package com.androsov.itmo_blps_lab1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class ItmoBlpsLab1Application {

    public static void main(String[] args) {
        SpringApplication.run(ItmoBlpsLab1Application.class, args);
    }

}
