package com.techrepair.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
public class WindowsNewsWindowLauncher {

    private static final Logger logger = LoggerFactory.getLogger(WindowsNewsWindowLauncher.class);
    private static final String NEWS_PATH = "/evidencia-noticias";

    @Value("${techrepair.windows.news-window.enabled:false}")
    private boolean enabled;

    @Value("${server.port:8080}")
    private int serverPort;

    @EventListener(ApplicationReadyEvent.class)
    public void openNewsWindow() {
        if (!enabled || !isWindows()) {
            return;
        }

        String url = "http://localhost:" + serverPort + NEWS_PATH;
        Path browserPath = resolveBrowserPath();

        if (browserPath == null) {
            logger.warn("No se encontro Microsoft Edge ni Google Chrome para abrir la ventana de noticias.");
            return;
        }

        List<String> command = new ArrayList<>();
        command.add(browserPath.toString());
        command.add("--new-window");
        command.add("--app=" + url);

        try {
            new ProcessBuilder(command).start();
            logger.info("La ventana de noticias tecnicas fue abierta en Windows: {}", url);
        } catch (IOException exception) {
            logger.warn("No fue posible abrir la ventana de noticias tecnicas en Windows.", exception);
        }
    }

    private boolean isWindows() {
        return System.getProperty("os.name", "").toLowerCase().contains("win");
    }

    private Path resolveBrowserPath() {
        for (String candidate : getBrowserCandidates()) {
            Path path = Path.of(candidate);
            if (Files.exists(path)) {
                return path;
            }
        }
        return null;
    }

    private List<String> getBrowserCandidates() {
        List<String> candidates = new ArrayList<>();
        addIfPresent(candidates, System.getenv("ProgramFiles"), "Microsoft\\Edge\\Application\\msedge.exe");
        addIfPresent(candidates, System.getenv("ProgramFiles(x86)"), "Microsoft\\Edge\\Application\\msedge.exe");
        addIfPresent(candidates, System.getenv("LocalAppData"), "Microsoft\\Edge\\Application\\msedge.exe");
        addIfPresent(candidates, System.getenv("ProgramFiles"), "Google\\Chrome\\Application\\chrome.exe");
        addIfPresent(candidates, System.getenv("ProgramFiles(x86)"), "Google\\Chrome\\Application\\chrome.exe");
        addIfPresent(candidates, System.getenv("LocalAppData"), "Google\\Chrome\\Application\\chrome.exe");
        return candidates;
    }

    private void addIfPresent(List<String> candidates, String basePath, String suffix) {
        if (basePath != null && !basePath.isBlank()) {
            candidates.add(basePath + "\\" + suffix);
        }
    }
}
