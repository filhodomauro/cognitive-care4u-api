package com.cognitivecare4u.cognitiveapi.images.storage;

import com.cognitivecare4u.cognitiveapi.images.ChildImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MockImageStorage implements ImageStorage {

    private Map<String, MultipartFile> images = new HashMap<>();

    @Override
    public String addImage(ChildImage childImage) {
        String imageId = UUID.randomUUID().toString();
        this.images.put(imageId, childImage.getFile());
        return "http://localhost:8080/" + imageId;
    }
}
