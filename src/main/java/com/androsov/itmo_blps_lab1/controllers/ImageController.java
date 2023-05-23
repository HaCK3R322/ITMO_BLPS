package com.androsov.itmo_blps_lab1.controllers;

import com.androsov.itmo_blps_lab1.annotations.FailOnGetParams;
import com.androsov.itmo_blps_lab1.entities.Image;
import com.androsov.itmo_blps_lab1.entities.User;
import com.androsov.itmo_blps_lab1.repositories.UserRepository;
import com.androsov.itmo_blps_lab1.servicies.ImageService;
import com.androsov.itmo_blps_lab1.servicies.UserService;
import lombok.AllArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.security.Principal;

@RestController
@CrossOrigin
@AllArgsConstructor
public class ImageController {
    ImageService imageService;
    UserService userService;

    @PostMapping(path = "/image/create")
    @FailOnGetParams
    public ResponseEntity<?> createImage(@RequestParam("file") MultipartFile file,
                                         Principal principal,
                                         HttpServletRequest request) {
        User user = userService.getByUsername(principal.getName());
        try {
            Tika tika = new Tika();
            String contentType = tika.detect(file.getBytes());
            if (contentType == null || (
                    !contentType.equals("image/png")
                            && !contentType.equals("image/jpeg")
                            && !contentType.equals("image/webp")
                    )
            ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file type \"" + contentType + "\". Only PNG and JPG files are allowed.");
            }

            // Check file extension
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || (!originalFilename.toLowerCase().endsWith(".png") && !originalFilename.toLowerCase().endsWith(".jpg"))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file extension. Only PNG and JPG files are allowed.");
            }

            // Get the bytes of the uploaded file
            byte[] imageData = file.getBytes();

            // Save the Image object to the database
            Image savedImage = imageService.createFromDataAndUser(imageData, user);

            // Return a response entity with the saved Image object and appropriate headers
            return ResponseEntity.status(HttpStatus.CREATED).body(savedImage);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sorry! You fucked up somehow (but file seems to be png or jpg for sure!)");
        }
    }

    @GetMapping(path = "/image/get/{id}")
    public ResponseEntity<Image> getImage(@PathVariable Long id) {
        Image image = imageService.getImageById(id);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
    }
}
