package com.cognitivecare4u.cognitiveapi.speeches;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/speeches")
public class SpeechController {

    @Autowired
    private SpeechService speechService;

    @PostMapping("/recognize/{resourceId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Speech> recognize(@RequestParam("file") MultipartFile file, @PathVariable String resourceId) {
        log.info("Multipart content: {}", file.getContentType());
        String text = speechService.recognize(resourceId, file);
        Speech speech = new Speech(text);
        return ResponseEntity.ok(speech);
    }
}
