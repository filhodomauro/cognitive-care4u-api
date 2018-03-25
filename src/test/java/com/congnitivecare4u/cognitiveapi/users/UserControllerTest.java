package com.congnitivecare4u.cognitiveapi.users;

import com.congnitivecare4u.cognitiveapi.CognitiveApiApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CognitiveApiApplication.class)
@WebAppConfiguration
public class UserControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup(){
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.userRepository.deleteAll();
    }

    @Autowired
    public void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Test
    public void testThatUserIsSaved() throws Exception {
        this.mockMvc.perform(
                post("/users")
                    .content(this.json(new User(null, "test user saved", "test@test.com")))
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
            )
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andExpect(header().string("location", startsWith("http://localhost/users/")));
    }

    @Test
    public void testThatUserIsNotFound() throws Exception {
        this.mockMvc.perform(
                get("/users/rfrfrfdfdsd")
        ).andExpect(status().isNotFound());
    }

    @Test
    public void testThatUserIsFound() throws Exception {
        String location =
                this.mockMvc.perform(
                post("/users")
                        .content(this.json(new User(null, "testThatUserIsFound", "test@test.com")))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
        ).andExpect(status().isCreated())
        .andReturn().getResponse().getHeader("location");

        String userId = location.substring(location.lastIndexOf("/") + 1);
        System.out.println("userId:" + userId);
        this.mockMvc.perform(
                get("/users/" + userId)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("testThatUserIsFound")));
    }

    @Test
    public void testThatUserIsRejectWithInvalidData() throws Exception {
        this.mockMvc.perform(
                post("/users")
                        .content(this.json(new User(null, null, "testtest.com")))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
        ).andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[*].field", containsInAnyOrder("name", "email")))
        .andExpect(jsonPath("$[*].errorMessage", containsInAnyOrder("must not be blank","must be a well-formed email address")));
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
