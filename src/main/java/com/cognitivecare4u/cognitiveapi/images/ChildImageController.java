package com.cognitivecare4u.cognitiveapi.images;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/children/{childId}/images")
public class ChildImageController {

    @Autowired
    private ChildImageService childImageService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ChildImage>> listImages(@PathVariable String childId) {
        List<ChildImage> images = childImageService.listImages(childId);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{imageId}/file")
    @ResponseBody
    public ResponseEntity<byte[]> get(@PathVariable String childId, @PathVariable String imageId) {
        byte[] file = childImageService.getImage(childId, imageId);
        return ResponseEntity.ok(file);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable String childId) {
        ChildImage childImage = new ChildImage(file);
        childImage = childImageService.saveImage(childId, childImage);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(childImage.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
