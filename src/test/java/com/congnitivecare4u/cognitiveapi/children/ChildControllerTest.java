package com.congnitivecare4u.cognitiveapi.children;

import com.congnitivecare4u.cognitiveapi.CognitiveApiApplication;
import com.congnitivecare4u.cognitiveapi.users.User;
import com.congnitivecare4u.cognitiveapi.users.UserRepository;
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

import java.util.Arrays;

import static com.congnitivecare4u.cognitiveapi.TestHelper.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CognitiveApiApplication.class)
@WebAppConfiguration
public class ChildControllerTest {

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
    public void testThatAChildIsCreated() throws Exception {
        String parentId = getNewParentId();

        this.mockMvc.perform(
                post("/children")
                        .content(toJson(new Child(null, "test child saved", Arrays.asList(parentId)), this.converter))
                        .contentType(JSON_CONTENT_TYPE)
                        .accept(JSON_CONTENT_TYPE)
        ).andExpect(status().isCreated())
        .andExpect(header().exists("location"))
        .andExpect(header().string("location", startsWith("http://localhost/children/")));
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
}
