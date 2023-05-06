package com.androsov.itmo_blps_lab1.controllers;

import com.androsov.itmo_blps_lab1.entities.Image;
import com.androsov.itmo_blps_lab1.entities.User;
import com.androsov.itmo_blps_lab1.repositories.UserRepository;
import com.androsov.itmo_blps_lab1.servicies.ImageService;
import com.androsov.itmo_blps_lab1.servicies.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@CrossOrigin
@AllArgsConstructor
public class ImageController {
    ImageService imageService;
    UserService userService;

    @PostMapping(path = "/image/create")
    public ResponseEntity<?> createImage(@RequestParam("file") MultipartFile file, Principal principal) {
        User user = userService.getByUsername(principal.getName());
        try {
            // Get the bytes of the uploaded file
            byte[] imageData = file.getBytes();

            // Save the Image object to the database
            Image savedImage = imageService.createFromDataAndUser(imageData, user);

            // Return a response entity with the saved Image object and appropriate headers
            return ResponseEntity.status(HttpStatus.CREATED).body(savedImage.getId());
        } catch (Exception e) {
            // Handle exceptions and return a response entity with an error message and appropriate headers
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    @GetMapping(path = "/image/get/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        byte[] data = imageService.getImageById(id).getData();

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(data);
    }
}
