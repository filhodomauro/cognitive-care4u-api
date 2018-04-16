package com.cognitivecare4u.cognitiveapi.children.images.storage;

import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public interface ImageStorage {
    String addImage(InputStream image);

    InputStream getImage(String path);
}
