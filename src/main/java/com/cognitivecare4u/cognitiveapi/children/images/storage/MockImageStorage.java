package com.cognitivecare4u.cognitiveapi.children.images.storage;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MockImageStorage implements ImageStorage {

    private Map<String, InputStream> images = new HashMap<>();

    @Override
    public String addImage(InputStream image) {
        String imageId = UUID.randomUUID().toString();
        String url = "http://localhost:8080/" + imageId;
        this.images.put(url, image);
        return url;
    }

    @Override
    public InputStream getImage(String path) {
        return images.get(path);
    }
}
