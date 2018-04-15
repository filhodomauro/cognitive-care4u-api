package com.cognitivecare4u.cognitiveapi.children.images;

import com.cognitivecare4u.cognitiveapi.exceptions.NotFoundException;
import com.cognitivecare4u.cognitiveapi.exceptions.UnprocessableEntityException;
import com.cognitivecare4u.cognitiveapi.children.images.cognitive.CognitiveService;
import com.cognitivecare4u.cognitiveapi.children.images.storage.ImageStorage;
import com.cognitivecare4u.cognitiveapi.visual_cognition.ClassificationResult;
import com.cognitivecare4u.cognitiveapi.visual_cognition.ClassifierClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ChildImageService {

    @Autowired
    private ChildImageRepository childImageRepository;

    @Autowired
    private ImageStorage imageStorage;

    @Autowired
    private CognitiveService cognitiveService;

    public ClassificationResult classify(final String childId, final String imageId) {
        InputStream image = getImage(childId, imageId);
        ClassificationResult result = cognitiveService.classify(image);
        Optional.of(result.getHighestScore()).ifPresent(classifierClass -> updateImage(imageId, classifierClass));
        return result;
    }

    public List<ChildImage> listImages(String childId) {
        return childImageRepository.findAllByChildId(childId);
    }

    public InputStream getImage(String childId, String imageId) {
        Optional<ChildImage> childImage = childImageRepository.findByIdAndChildId(imageId, childId);
        if (!childImage.isPresent()) {
            throw new NotFoundException();
        }
        return imageStorage.getImage(childImage.get().getOriginalPath());
    }

    public ChildImage saveImage(String childId, ChildImage childImage) {
        String path = null;
        try {
            path = imageStorage.addImage(childImage.getFile().getInputStream());
        } catch (IOException e) {
            throw new UnprocessableEntityException("Invalid image");
        }
        childImage.setChildId(childId);
        childImage.setOriginalPath(path);
        return childImageRepository.save(childImage);
    }

    private void updateImage(final String imageId, final ClassifierClass classifierClass) {
        childImageRepository.findById(imageId).ifPresent(childImage -> {
            childImage.setClassifierClass(classifierClass);
            childImageRepository.save(childImage);
        });
    }
}
