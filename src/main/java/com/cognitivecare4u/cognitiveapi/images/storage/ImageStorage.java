package com.cognitivecare4u.cognitiveapi.images.storage;

import com.cognitivecare4u.cognitiveapi.images.ChildImage;
import org.springframework.stereotype.Component;

@Component
public interface ImageStorage {
    String addImage(ChildImage childImage);
}
