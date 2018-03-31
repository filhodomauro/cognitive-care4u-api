package com.cognitivecare4u.cognitiveapi.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Resource getImage(String imageId) {
        return null;
    }

    public Image saveImage(String childId, Image image) {
        return imageRepository.save(image);
    }
}
