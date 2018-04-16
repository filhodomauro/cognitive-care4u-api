package com.cognitivecare4u.cognitiveapi.children.images.storage;

import java.io.*;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MockImageStorage implements ImageStorage {

    private Map<String, InputStream> images = new HashMap<>();
    private final File IMAGE_DIR;
    public MockImageStorage(String activeProfile) {
        String imagePath = System.getProperty("user.home") + File.separator + ".care-4u" + File.separator + activeProfile;
        IMAGE_DIR = new File(imagePath);
        if (!IMAGE_DIR.exists()) {
            IMAGE_DIR.mkdirs();
        }
    }

    @Override
    public String addImage(InputStream image) {
        String imageId = UUID.randomUUID().toString();
        String url = "http://localhost:8080/" + imageId;
        this.images.put(url, image);

        File imageFile = new File(IMAGE_DIR, imageId + ".jpg");

        try {
            java.nio.file.Files.copy(
                    image,
                    imageFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error to save image", e);
        }
        return url;
    }

    @Override
    public InputStream getImage(String path) {
        String imageId = path.substring(path.lastIndexOf("/") + 1);
        File imageFile = new File(IMAGE_DIR, imageId + ".jpg");
        try {
            return new FileInputStream(imageFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error to read image", e);
        }
    }
}
