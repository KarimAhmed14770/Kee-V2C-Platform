package com.Kee.V2C.utils;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class ImageUtil {
    //should be replaced with @value from application.properties
    private final String BASE_DIR = "D:/Studies/Programming/Java/projects/E-commerce/uploads/";

    public String convertRelativeImageToBase64(String relativePath) {
        try {
            // 1. Create the Path for the root directory
            Path root = Paths.get(BASE_DIR);

            // 2. Resolve the relative path against the root
            Path fullPath = root.resolve(relativePath).normalize();

            // 3. Convert to bytes and then to Base64
            byte[] imageBytes = Files.readAllBytes(fullPath);
            return Base64.getEncoder().encodeToString(imageBytes);

        } catch (Exception e) {
            throw new RuntimeException("Could not find or read file: " + relativePath, e);
        }
    }

    public void saveImageBytes(byte[] imageBytes, String fileName) {
        try {
            // 1. Define where you want to save it
            Path path = Paths.get("D:/Studies/Programming/Java/projects/E-commerce/uploads/outputs" + fileName);

            // 2. Create directories if they don't exist
            if (Files.notExists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            // 3. Write the bytes to the file
            Files.write(path, imageBytes);

            System.out.println("Image saved successfully at: " + path.toAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

