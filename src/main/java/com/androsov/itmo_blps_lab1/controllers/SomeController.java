package com.androsov.itmo_blps_lab1.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class SomeController {
    @GetMapping("/home")
    public String home() {
        return "welcome!";
    }
}
