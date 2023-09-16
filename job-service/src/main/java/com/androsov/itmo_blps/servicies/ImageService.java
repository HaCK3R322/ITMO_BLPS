package com.androsov.itmo_blps.servicies;

import com.androsov.itmo_blps.model.entities.Image;
import com.androsov.itmo_blps.model.User;
import com.androsov.itmo_blps.repositories.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageService {
    private ImageRepository imageRepository;
    private UserService userService;

    public Image getById(Long id) throws EntityNotFoundException, AccessDeniedException {
        Optional<Image> imageOptional = imageRepository.findById(id);

        if(imageOptional.isEmpty())
            throw new EntityNotFoundException("Image with id " + id + " not found!");

        if(!currentPrincipalHasAccessToImage(imageOptional.get())) { // TODO: role HR and admin can do that too
            throw new AccessDeniedException("User with username "
                    + SecurityContextHolder.getContext().getAuthentication().getName()
                    + " has no access to image with id " + id);
        }

        return imageOptional.get();
    }

    public boolean currentPrincipalHasAccessToImage(Image image) throws EntityNotFoundException {
        User user = userService.getById(image.getUserId());
        String imageUsername = user.getUsername();
        String principalName = SecurityContextHolder.getContext().getAuthentication().getName();
        return principalName.equals(imageUsername);
    }

    public Image createFromDataAndUser(byte[] data, User user) {
        return imageRepository.save(new Image(null, data, user.getId()));
    }

    public boolean existsById(Long id) {
        return imageRepository.existsById(id);
    }
    public boolean exists(Image image) {
        return existsById(image.getId());
    }
}
