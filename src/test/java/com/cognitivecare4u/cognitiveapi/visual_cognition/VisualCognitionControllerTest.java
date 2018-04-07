package com.cognitivecare4u.cognitiveapi.visual_cognition;

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
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CognitiveApiApplication.class)
@WebAppConfiguration
public class VisualCognitionControllerTest {

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
    public void testThatAValidImageIsClassified() throws Exception {
        String imageId = "image_id_valid";
        this.mockMvc.perform(
                post("/visual-cognition/classify")
                        .contentType(JSON_CONTENT_TYPE)
                        .content(toJson(new Message(imageId), this.converter))
        ).andExpect(status().isOk());
    }
}
