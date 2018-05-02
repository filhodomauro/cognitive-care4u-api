package com.cognitivecare4u.cognitiveapi.speeches;

import com.cognitivecare4u.cognitiveapi.speeches.cognitive.CognitiveSpeechToText;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class SpeechService {

    @Autowired
    private CognitiveSpeechToText cognitiveSpeechToText;

    public String recognize(String resourceId, MultipartFile file) {
        InputStream audio = null;
        try {
            audio = file.getInputStream();
        } catch (IOException e) {
            log.error("Error to read audio", e);
        }
        String text = cognitiveSpeechToText.transform(audio, file.getContentType());
        log.info(text);
        return text;
    }
}
