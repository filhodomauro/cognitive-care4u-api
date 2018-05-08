package com.cognitivecare4u.cognitiveapi.children.analyze;

import com.cognitivecare4u.cognitiveapi.CognitiveApiApplication;
import com.cognitivecare4u.cognitiveapi.children.analyse.ChildAnalyze;
import com.cognitivecare4u.cognitiveapi.children.analyse.ChildAnalyzeRequest;
import com.cognitivecare4u.cognitiveapi.children.analyse.ChildAnalyzeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CognitiveApiApplication.class)
public class ChildAnalyzeServiceTest {

    @Autowired
    private ChildAnalyzeService childAnalyzeService;

    @Test
    public void analyzeTextFromRemote() {
        ChildAnalyzeRequest analyzeRequest = new ChildAnalyzeRequest();
        analyzeRequest.setContentType("audio/mpeg");
        analyzeRequest.setResourcePath("https://firebasestorage.googleapis.com/v0/b/cc4y-fiap.appspot.com/o/teste.mp3?alt=media&token=7d130718-ea7f-4874-918f-e0f8ca5b93b0");
        childAnalyzeService.speechAnalyse(analyzeRequest);
    }

    @Test
    public void analyzeImageFromRemote() {
        ChildAnalyzeRequest analyzeRequest = new ChildAnalyzeRequest();
        analyzeRequest.setContentType("image/jpeg");
        analyzeRequest.setResourcePath("https://firebasestorage.googleapis.com/v0/b/cc4y-fiap.appspot.com/o/crianca.jpg?alt=media&token=ceea82bb-705c-4b7e-a4ab-eb0a4438f28d");
        ChildAnalyze childAnalyze = childAnalyzeService.imageAnalyse(analyzeRequest);
        assertThat(childAnalyze.getHappy(), greaterThan(0f));
        assertThat(childAnalyze.getSad(), equalTo(0f));
    }
}
