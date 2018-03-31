package com.cognitivecare4u.cognitiveapi.images;

import com.cognitivecare4u.cognitiveapi.exceptions.NotFoundException;
import com.cognitivecare4u.cognitiveapi.images.storage.ImageStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChildImageService {

    @Autowired
    private ChildImageRepository childImageRepository;

    @Autowired
    private ImageStorage imageStorage;

    public List<ChildImage> listImages(String childId) {
        return childImageRepository.findAllByChildId(childId);
    }

    public byte[] getImage(String childId, String imageId) {
        Optional<ChildImage> childImage = childImageRepository.findByIdAndChildId(imageId, childId);
        if (!childImage.isPresent()) {
            throw new NotFoundException();
        }
        return imageStorage.getImage(childImage.get());
    }

    public ChildImage saveImage(String childId, ChildImage childImage) {
        String path = imageStorage.addImage(childImage);
        childImage.setChildId(childId);
        childImage.setOriginalPath(path);
        return childImageRepository.save(childImage);
    }
}
