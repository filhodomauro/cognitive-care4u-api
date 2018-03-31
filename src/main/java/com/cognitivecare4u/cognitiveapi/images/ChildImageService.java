package com.cognitivecare4u.cognitiveapi.images;

import com.cognitivecare4u.cognitiveapi.images.storage.ImageStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ChildImageService {

    @Autowired
    private ChildImageRepository childImageRepository;

    @Autowired
    private ImageStorage imageStorage;

    public Resource getImage(String imageId) {
        return null;
    }

    public ChildImage saveImage(String childId, ChildImage childImage) {
        String path = imageStorage.addImage(childImage);
        childImage.setChildId(childId);
        childImage.setOriginalPath(path);
        return childImageRepository.save(childImage);
    }
}
