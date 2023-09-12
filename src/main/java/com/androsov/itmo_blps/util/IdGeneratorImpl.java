package com.androsov.itmo_blps.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class IdGeneratorImpl implements IdGenerator {

    @Value("${user.xml.lastUsedId}")
    private String lastUsedIdFilePath;

    private Long lastUsedId;

    @PostConstruct
    public void init() {
        File file = new File(lastUsedIdFilePath);

        if (file.exists()) {
            try {
                String content = new String(FileCopyUtils.copyToByteArray(file), StandardCharsets.UTF_8);
                lastUsedId = Long.parseLong(content.trim());
            } catch (IOException e) {
                throw new RuntimeException("Error reading last used ID from file", e);
            }
        } else {
            lastUsedId = 0L; // Set the default value when the file doesn't exist
            saveLastUsedIdToFile();
        }
    }

    private void saveLastUsedIdToFile() {
        try {
            File file = new File(lastUsedIdFilePath);
            FileCopyUtils.copy(lastUsedId.toString().getBytes(StandardCharsets.UTF_8), file);
        } catch (IOException e) {
            throw new RuntimeException("Error saving last used ID to file", e);
        }
    }

    @Override
    public synchronized Long generateNewId() {
        lastUsedId++;
        saveLastUsedIdToFile();
        return lastUsedId;
    }

    @Override
    public void setLastId(Long newLastId) {
        lastUsedId = newLastId;
        saveLastUsedIdToFile();
    }
}
