package com.androsov.itmo_blps.servicies;

import com.androsov.itmo_blps.entities.Image;
import com.androsov.itmo_blps.entities.User;
import com.androsov.itmo_blps.repositories.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class ImageService {
    private ImageRepository imageRepository;

    public Image getImageById(Long id) throws EntityNotFoundException {

        return imageRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Image createFromDataAndUser(byte[] data, User user) {
        return imageRepository.save(new Image(null, data, user));
    }

    public boolean existsById(Long id) {
        return imageRepository.existsById(id);
    }
}
