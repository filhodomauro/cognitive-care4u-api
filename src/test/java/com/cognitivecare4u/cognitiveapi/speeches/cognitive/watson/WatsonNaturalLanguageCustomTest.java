package com.cognitivecare4u.cognitiveapi.speeches.cognitive.watson;

import com.cognitivecare4u.cognitiveapi.CognitiveApiApplication;
import com.cognitivecare4u.cognitiveapi.analyzes.Analyze;
import com.cognitivecare4u.cognitiveapi.speeches.cognitive.CognitiveNaturalLanguage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CognitiveApiApplication.class)
public class WatsonNaturalLanguageCustomTest {

    @Autowired
    private CognitiveNaturalLanguage service;

    @Test
    public void testThatSpeechIsClassified() {
        String speech = "A professora é muito legal";
        Analyze analyze = service.analyze(speech);
        assertThat(analyze.getHappy(), greaterThan(0f));
        assertThat(analyze.getSad(), lessThan(0.5f));
        assertThat(analyze.getSexualContent(), lessThan(0.5f));
        assertThat(analyze.getBullying(), lessThan(0.5f));
        assertThat(analyze.getProfanity(), lessThan(0.5f));
    }
}
