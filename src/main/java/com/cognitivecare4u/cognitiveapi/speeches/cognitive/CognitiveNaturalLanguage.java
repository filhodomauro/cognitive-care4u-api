package com.cognitivecare4u.cognitiveapi.speeches.cognitive;

import com.cognitivecare4u.cognitiveapi.analyzes.Analyze;
import org.springframework.stereotype.Component;

@Component
public interface CognitiveNaturalLanguage {

    Analyze analyze(String speech);
}
