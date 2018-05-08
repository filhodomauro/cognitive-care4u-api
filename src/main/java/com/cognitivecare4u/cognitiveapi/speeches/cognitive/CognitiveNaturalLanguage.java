package com.cognitivecare4u.cognitiveapi.speeches.cognitive;

import com.cognitivecare4u.cognitiveapi.children.analyse.ChildAnalyze;
import org.springframework.stereotype.Component;

@Component
public interface CognitiveNaturalLanguage {

    ChildAnalyze analyze(String speech);
}
