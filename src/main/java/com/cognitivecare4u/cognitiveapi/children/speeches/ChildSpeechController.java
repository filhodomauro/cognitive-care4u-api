package com.cognitivecare4u.cognitiveapi.children.speeches;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@Controller
@RequestMapping("/children/{childId}/speeches")
public class ChildSpeechController {

    @Autowired
    private ChildSpeechService childSpeechService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody @Valid ChildSpeech speech, @PathVariable String childId, BindingResult bindingResult) {
        speech.setChildId(childId);
        ChildSpeech childSpeech = childSpeechService.save(speech);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(childSpeech.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
