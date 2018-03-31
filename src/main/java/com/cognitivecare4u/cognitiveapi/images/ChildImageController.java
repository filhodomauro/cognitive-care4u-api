package com.cognitivecare4u.cognitiveapi.images;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@Controller
@RequestMapping("/children/{childId}/images")
public class ChildImageController {

    @Autowired
    private ChildImageService childImageService;

    @GetMapping("/{imageId}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String childId, @PathVariable String imageId) {
        Resource file = childImageService.getImage(imageId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
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
