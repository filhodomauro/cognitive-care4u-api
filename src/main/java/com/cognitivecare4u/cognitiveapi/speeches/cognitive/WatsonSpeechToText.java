package com.cognitivecare4u.cognitiveapi.speeches.cognitive;

import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;

@Slf4j
@Component
public class WatsonSpeechToText implements CognitiveSpeechToText {

    @Value("${care4u.speeches.watson.username}")
    private String username;

    @Value("${care4u.speeches.watson.password}")
    private String password;

    @Value("${care4u.speeches.watson.language_model}")
    private String languageModel;

    private SpeechToText speechToText;

    @Override
    public String transform(InputStream speech, String contentType) {
        assert speech != null;

        StringBuilder text = new StringBuilder();

        RecognizeOptions options =
                new RecognizeOptions.Builder()
                        .audio(speech)
                        .contentType(contentType)
                        .model(this.languageModel)
                .build();
        SpeechRecognitionResults results = this.speechToText.recognize(options).execute();

        log.info("Speech To Text: {}",results);

        if (results.getResults() != null) {
            results.getResults().forEach(result -> {
                if (result.getAlternatives() != null) {
                    result.getAlternatives().forEach(alternative -> {
                        text.append(alternative.getTranscript()).append(".");
                    });
                }
            });
        }
        return text.toString();
    }

    @PostConstruct
    public void configure() {
        this.speechToText = new SpeechToText(this.username, this.password);
    }
}
