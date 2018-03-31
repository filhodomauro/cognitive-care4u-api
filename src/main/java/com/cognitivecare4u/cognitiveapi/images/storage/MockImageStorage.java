package com.cognitivecare4u.cognitiveapi.images.storage;

import com.cognitivecare4u.cognitiveapi.exceptions.UnprocessableEntityException;
import com.cognitivecare4u.cognitiveapi.images.ChildImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MockImageStorage implements ImageStorage {

    private Map<String, byte[]> images = new HashMap<>();

    @Override
    public String addImage(ChildImage childImage) {
        String imageId = UUID.randomUUID().toString();
        String url = "http://localhost:8080/" + imageId;
        try {
            this.images.put(url, childImage.getFile().getBytes());
        } catch (IOException e) {
            throw new UnprocessableEntityException("invalid image");
        }
        return url;
    }

    @Override
    public byte[] getImage(ChildImage childImage) {
        return images.get(childImage.getOriginalPath());
    }
}
