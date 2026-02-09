package com.techrepair.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final Path storage = Paths.get("uploads");

    public FileStorageService() throws IOException {
        if (!Files.exists(storage)) Files.createDirectories(storage);
    }

    public String store(MultipartFile file) throws IOException {
        Path dest = storage.resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), dest);
        return dest.toString();
    }

    public Path load(String filename) {
        return storage.resolve(filename);
    }
}
