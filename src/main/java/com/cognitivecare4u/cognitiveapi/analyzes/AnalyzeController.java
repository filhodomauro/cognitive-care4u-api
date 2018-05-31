package com.cognitivecare4u.cognitiveapi.analyzes;

import com.cognitivecare4u.cognitiveapi.exceptions.UnprocessableEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/analyzes/")
public class AnalyzeController {

    @Autowired
    private AnalyzeService analyzeService;

    @PostMapping("/images")
    public ResponseEntity<Analyze> images(@RequestBody @Valid AnalyzeRequest analyzeRequest,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UnprocessableEntityException("Error to request image analyze", bindingResult.getFieldErrors());
        }
        return ResponseEntity.ok(analyzeService.imageAnalyse(analyzeRequest));
    }

    @PostMapping("/speeches")
    public ResponseEntity<Analyze> speeches(@RequestBody @Valid AnalyzeRequest analyzeRequest,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UnprocessableEntityException("Error to request speech analyze", bindingResult.getFieldErrors());
        }
        return ResponseEntity.ok(analyzeService.speechAnalyse(analyzeRequest));
    }
}
