package com.cognitivecare4u.cognitiveapi.speeches;

import com.cognitivecare4u.cognitiveapi.CognitiveApiApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;

import static org.hamcrest.Matchers.*;
import static com.cognitivecare4u.cognitiveapi.TestHelper.findConverter;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CognitiveApiApplication.class)
@WebAppConfiguration
public class SpeechControllerTest {

    private final String TEST_MESSAGE = "teste de transformação de fala em texto .";

    private MockMvc mockMvc;

    private HttpMessageConverter converter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup(){
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    public void setConverters(HttpMessageConverter<?>[] converters) {

        this.converter = findConverter(converters);

        assertNotNull("the JSON message converter must not be null",
                this.converter);
    }

    @Test
    public void testSendingAudioToCognitiveService() throws Exception {
        byte[] audio = Files.readAllBytes(ResourceUtils.getFile("classpath:speeches/fala_em_texto_ptBR.ogg").toPath());
        MockMultipartFile multipartFile =
                new MockMultipartFile("file", audio);
        this.mockMvc.perform(
                multipart("/speeches/recognize/resourd_id_1").file(multipartFile)
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.text", is(TEST_MESSAGE)));
    }
}
