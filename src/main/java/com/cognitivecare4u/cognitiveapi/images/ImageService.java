package com.cognitivecare4u.cognitiveapi.images;

import com.cognitivecare4u.cognitiveapi.images.storage.ImageStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageStorage imageStorage;

    public Resource getImage(String imageId) {
        return null;
    }

    public Image saveImage(String childId, Image image) {
        String path = imageStorage.addImage(image);
        image.setChildId(childId);
        image.setOriginalPath(path);
        return imageRepository.save(image);
    }
}
