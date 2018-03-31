package com.cognitivecare4u.cognitiveapi.images.storage;

import com.cognitivecare4u.cognitiveapi.images.Image;
import org.springframework.stereotype.Component;

@Component
public interface ImageStorage {
    String addImage(Image image);
}
