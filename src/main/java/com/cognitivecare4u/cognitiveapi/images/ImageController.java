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

@Slf4j
@Controller
@RequestMapping("/children/{childId}/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/{imageId}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String childId, @PathVariable String imageId) {
        Resource file = imageService.getImage(imageId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void handleFileUpload(@PathVariable String childId, @RequestParam("file") MultipartFile file) {
        log.error("Child id {}", childId);
        log.error("Multipart file {}", file.getName());
        imageService.saveImage(childId, file);
    }
}
