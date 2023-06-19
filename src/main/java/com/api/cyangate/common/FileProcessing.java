package com.api.cyangate.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileProcessing {
    public static void deleteFile(String path) throws IOException {
        Files.delete(Path.of(path));
    }

    public static void downloadImage(byte[] imageBytes, String name, Path path) throws Exception {
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }

        path = Paths.get(path.toString(), name);
        Files.write(path, imageBytes);
    }
}
