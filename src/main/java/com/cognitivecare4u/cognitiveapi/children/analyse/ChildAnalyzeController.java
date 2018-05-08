package com.cognitivecare4u.cognitiveapi.children.analyse;

import com.cognitivecare4u.cognitiveapi.exceptions.UnprocessableEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/children/{childId}/analyzes")
public class ChildAnalyzeController {

    @Autowired
    private ChildAnalyzeService childAnalyzeService;

    @PostMapping("/images")
    public ResponseEntity<ChildAnalyze> images(@PathVariable String childId,
                                               @RequestBody @Valid ChildAnalyzeRequest analyzeRequest,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UnprocessableEntityException("Error to request image analyze", bindingResult.getFieldErrors());
        }
        return ResponseEntity.ok(childAnalyzeService.imageAnalyse(analyzeRequest));
    }

    @PostMapping("/speeches")
    public ResponseEntity<ChildAnalyze> speeches(@PathVariable String childId,
                                                 @RequestBody @Valid ChildAnalyzeRequest analyzeRequest,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UnprocessableEntityException("Error to request speech analyze", bindingResult.getFieldErrors());
        }
        return ResponseEntity.ok(childAnalyzeService.speechAnalyse(analyzeRequest));
    }
}
