package com.cognitivecare4u.cognitiveapi.images;

import com.cognitivecare4u.cognitiveapi.CognitiveApiApplication;
import com.cognitivecare4u.cognitiveapi.children.Child;
import com.cognitivecare4u.cognitiveapi.children.ChildRepository;
import com.cognitivecare4u.cognitiveapi.users.User;
import com.cognitivecare4u.cognitiveapi.users.UserRepository;
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
import java.util.Arrays;

import static com.cognitivecare4u.cognitiveapi.TestHelper.JSON_CONTENT_TYPE;
import static com.cognitivecare4u.cognitiveapi.TestHelper.findConverter;
import static com.cognitivecare4u.cognitiveapi.TestHelper.toJson;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CognitiveApiApplication.class)
@WebAppConfiguration
public class ImageControllerTest {

    private MockMvc mockMvc;

    private HttpMessageConverter converter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup(){
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        childRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Autowired
    public void setConverters(HttpMessageConverter<?>[] converters) {

        this.converter = findConverter(converters);

        assertNotNull("the JSON message converter must not be null",
                this.converter);
    }

    @Test
    public void testThatAnImageIsUpload() throws Exception {
        String parentId = getNewParentId();
        String childId = getNewChildFromParentId(parentId);
        byte[] image = Files.readAllBytes(ResourceUtils.getFile("classpath:images/happy_child.jpg").toPath());
        MockMultipartFile multipartFile =
                new MockMultipartFile("file", image);
        String path = "/children/"+ childId+ "/images";
        this.mockMvc.perform(
                multipart(path).file(multipartFile)
        ).andExpect(status().isCreated())
        .andExpect(header().exists("location"))
        .andExpect(header().string("location", startsWith("http://localhost" + path)));;
    }

    private String getNewParentId() throws Exception {
        String location =
                this.mockMvc.perform(
                        post("/users")
                                .content(toJson(new User(null, "getNewParentId", "parent@child.com"), converter))
                                .contentType(JSON_CONTENT_TYPE)
                                .accept(JSON_CONTENT_TYPE)
                ).andExpect(status().isCreated())
                        .andReturn().getResponse().getHeader("location");

        return location.substring(location.lastIndexOf("/") + 1);
    }

    private String getNewChildFromParentId(String parentId) throws Exception {
        String location =
                this.mockMvc.perform(
                        post("/children")
                                .content(toJson(new Child("test child saved", Arrays.asList(parentId)), this.converter))
                                .contentType(JSON_CONTENT_TYPE)
                                .accept(JSON_CONTENT_TYPE)
                ).andExpect(status().isCreated())
                        .andReturn().getResponse().getHeader("location");

        return location.substring(location.lastIndexOf("/") + 1);
    }
}
