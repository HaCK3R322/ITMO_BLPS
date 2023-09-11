package com.androsov.itmo_blps.repositories.implementations;

import com.androsov.itmo_blps.model.User;
import com.androsov.itmo_blps.repositories.UserRepository;
import com.androsov.itmo_blps.util.IdGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class XmlFileUserRepository implements UserRepository {
    private final XmlMapper xmlMapper = new XmlMapper();
    private final IdGenerator idGenerator;

    @Value("${user.xml.file}")
    private String xmlFilePath;

    @Override
    public User save(User user) {
        List<User> userList = getUsersFromXML();

        if (user.getId() == null) {
            Long newId = idGenerator.generateNewId();
            user.setId(newId);
            userList.add(user);
        } else {
            int index = -1;
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getId().equals(user.getId())) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                userList.set(index, user);
            } else {
                throw new RuntimeException("User with ID " + user.getId() + " not found for update.");
            }
        }

        saveUsersToXML(userList);
        return user;
    }

    @Override
    public Optional<User> getByUsername(String username) {
        List<User> userList = getUsersFromXML();
        return userList.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Optional<User> getById(Long id) {
        List<User> userList = getUsersFromXML();
        return userList.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public User deleteById(Long userId) {
        List<User> userList = getUsersFromXML();

        Optional<User> userToDelete = userList.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst();

        if (userToDelete.isPresent()) {
            userList.remove(userToDelete.get());
            saveUsersToXML(userList);
            return userToDelete.get();
        } else {
            throw new EntityNotFoundException("User with ID " + userId + " not found for deletion.");
        }
    }

    private List<User> getUsersFromXML() {
        File file = new File(xmlFilePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            return xmlMapper.readValue(file, new TypeReference<List<User>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error reading users from XML", e);
        }
    }

    private void saveUsersToXML(List<User> userList) {
        try {
            xmlMapper.writeValue(new File(xmlFilePath), userList);
        } catch (IOException e) {
            throw new RuntimeException("Error writing users to XML", e);
        }
    }
}

