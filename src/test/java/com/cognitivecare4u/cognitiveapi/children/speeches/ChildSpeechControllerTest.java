package com.cognitivecare4u.cognitiveapi.children.speeches;

import com.cognitivecare4u.cognitiveapi.CognitiveApiApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static com.cognitivecare4u.cognitiveapi.TestHelper.JSON_CONTENT_TYPE;
import static com.cognitivecare4u.cognitiveapi.TestHelper.findConverter;
import static com.cognitivecare4u.cognitiveapi.TestHelper.toJson;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CognitiveApiApplication.class)
@WebAppConfiguration
public class ChildSpeechControllerTest {

    private MockMvc mockMvc;

    private HttpMessageConverter converter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ChildSpeechRepository childSpeechRepository;

    @Before
    public void setup(){
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        childSpeechRepository.deleteAll();
    }

    @Autowired
    public void setConverters(HttpMessageConverter<?>[] converters) {

        this.converter = findConverter(converters);

        assertNotNull("the JSON message converter must not be null",
                this.converter);
    }

    @Test
    public void testThatUserIsSaved() throws Exception {
        this.mockMvc.perform(
                post("/children/67890/speeches")
                        .content(toJson(new ChildSpeech(null, "67890", null, "http://localhost/audios/789797", "audio/ogg"), this.converter))
                        .contentType(JSON_CONTENT_TYPE)
                        .accept(JSON_CONTENT_TYPE)
        )
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andExpect(header().string("location", startsWith("http://localhost/children/67890/speeches/")));
    }
}
