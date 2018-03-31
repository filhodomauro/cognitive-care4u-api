package com.cognitivecare4u.cognitiveapi.images;

import com.cognitivecare4u.cognitiveapi.images.storage.ImageStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildImageService {

    @Autowired
    private ChildImageRepository childImageRepository;

    @Autowired
    private ImageStorage imageStorage;

    public List<ChildImage> listImages(String childId) {
        return childImageRepository.findAllByChildId(childId);
    }

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
