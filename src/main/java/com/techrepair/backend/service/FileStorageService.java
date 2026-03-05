package com.techrepair.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path storage = Paths.get("uploads");

    public FileStorageService() throws IOException {
        if (!Files.exists(storage)) Files.createDirectories(storage);
    }

    public String store(MultipartFile file) throws IOException {
        String originalName = StringUtils.cleanPath(Objects.requireNonNullElse(file.getOriginalFilename(), "archivo.bin"));
        String safeName = originalName.replaceAll("[^a-zA-Z0-9._-]", "_");
        String storedName = System.currentTimeMillis() + "_" + UUID.randomUUID() + "_" + safeName;
        Path dest = storage.resolve(storedName).normalize();

        if (!dest.startsWith(storage)) {
            throw new IOException("Invalid file path");
        }

        Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);
        return storedName;
    }

    public Path load(String filename) {
        return storage.resolve(filename).normalize();
    }
}
