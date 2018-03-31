package com.cognitivecare4u.cognitiveapi.images.storage;

import com.cognitivecare4u.cognitiveapi.images.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MockImageStorage implements ImageStorage {

    private Map<String, MultipartFile> images = new HashMap<>();

    @Override
    public String addImage(Image image) {
        String imageId = UUID.randomUUID().toString();
        this.images.put(imageId, image.getFile());
        return "http://localhost:8080/" + imageId;
    }
}
