package com.cognitivecare4u.cognitiveapi.visual_cognition;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visual-cognition")
public class VisualCognitionController {

    @PostMapping("/classify")
    @ResponseStatus(HttpStatus.OK)
    public void classify(@RequestBody Message message) {

    }

}
