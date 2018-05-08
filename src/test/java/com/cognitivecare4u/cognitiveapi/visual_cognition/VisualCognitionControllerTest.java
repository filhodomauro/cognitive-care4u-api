package com.cognitivecare4u.cognitiveapi.visual_cognition;

import com.cognitivecare4u.cognitiveapi.CognitiveApiApplication;
import com.cognitivecare4u.cognitiveapi.children.Child;
import com.cognitivecare4u.cognitiveapi.children.ChildRepository;
import com.cognitivecare4u.cognitiveapi.children.images.ChildImage;
import com.cognitivecare4u.cognitiveapi.children.images.ChildImageRepository;
import com.cognitivecare4u.cognitiveapi.children.images.cognitive.VisualCognitiveService;
import com.cognitivecare4u.cognitiveapi.children.images.storage.ImageStorage;
import com.cognitivecare4u.cognitiveapi.users.User;
import com.cognitivecare4u.cognitiveapi.users.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;

import static com.cognitivecare4u.cognitiveapi.TestHelper.JSON_CONTENT_TYPE;
import static com.cognitivecare4u.cognitiveapi.TestHelper.findConverter;
import static com.cognitivecare4u.cognitiveapi.TestHelper.toJson;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private ChildImageRepository childImageRepository;

    @MockBean
    private VisualCognitiveService visualCognitiveService;

    @MockBean
    private ImageStorage imageStorage;

    @Before
    public void setup(){
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        childImageRepository.deleteAll();
        childRepository.deleteAll();
        userRepository.deleteAll();
        createChildImage();
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
        String childId = "child_id";
        byte[] image = Files.readAllBytes(ResourceUtils.getFile("classpath:images/happy_child.jpg").toPath());
        ClassificationResult classificationResult = getClassificationResult();

        when(imageStorage.getImage("http://localhost:7777/images/image_id_valid"))
                .then(invocation -> new ByteArrayInputStream(image));
        when(visualCognitiveService.classify(ArgumentMatchers.any(InputStream.class)))
                .then(invocation -> classificationResult );
        this.mockMvc.perform(
                post("/visual-cognition/classify")
                        .contentType(JSON_CONTENT_TYPE)
                        .content(toJson(new Message(childId, imageId), this.converter))
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.classifierId", is(classificationResult.getClassifierId())))
        .andExpect(jsonPath("$.highestScore.name", is("happy")));
    }

    private ChildImage createChildImage() {
        Child child = createChild();
        ChildImage childImage =
                new ChildImage("image_id_valid", child.getId(), null, "http://localhost:7777/images/image_id_valid", null );
        return childImageRepository.save(childImage);
    }

    private User createParent() {
        User user = new User(null,"visual parent", "visual@parent.com");
        return userRepository.save(user);
    }

    private Child createChild() {
        User parent = createParent();
        Child child = new Child("child_id", "child name", Arrays.asList(parent.getId()), null);
        return childRepository.save(child);
    }

    private ClassificationResult getClassificationResult() {
        ClassifierClass classifierClass = new ClassifierClass("happy", 0.9f);
        ClassificationResult classificationResult =
                new ClassificationResult(
                        "classifier_id",
                        classifierClass,
                        Arrays.asList(classifierClass));
        return classificationResult;
    }
}
