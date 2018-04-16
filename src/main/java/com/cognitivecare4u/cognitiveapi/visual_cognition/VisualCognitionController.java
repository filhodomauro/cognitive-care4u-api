package com.cognitivecare4u.cognitiveapi.visual_cognition;

import com.cognitivecare4u.cognitiveapi.children.images.ChildImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visual-cognition")
public class VisualCognitionController {

    @Autowired
    private ChildImageService childImageService;

    @PostMapping("/classify")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ClassificationResult> classify(@RequestBody Message message) {
        return ResponseEntity.ok(childImageService.classify(message.getChildId(), message.getImageId()));
    }

}
