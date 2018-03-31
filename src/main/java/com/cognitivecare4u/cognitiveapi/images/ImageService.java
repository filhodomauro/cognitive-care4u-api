package com.cognitivecare4u.cognitiveapi.images;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    public Resource getImage(String imageId) {
        return null;
    }

    public Image saveImage(String childId, Image image) {
        image.setId("444444");
        return image;
    }
}
