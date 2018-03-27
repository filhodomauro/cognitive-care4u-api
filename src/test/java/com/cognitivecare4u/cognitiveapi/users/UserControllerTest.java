package com.cognitivecare4u.cognitiveapi.users;

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

import static com.cognitivecare4u.cognitiveapi.TestHelper.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CognitiveApiApplication.class)
@WebAppConfiguration
public class UserControllerTest {

    private MockMvc mockMvc;

    private HttpMessageConverter converter;

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

        this.converter = findConverter(converters);

        assertNotNull("the JSON message converter must not be null",
                this.converter);
    }

    @Test
    public void testThatUserIsSaved() throws Exception {
        this.mockMvc.perform(
                post("/users")
                    .content(toJson(new User(null, "test user saved", "test@test.com"), this.converter))
                    .contentType(JSON_CONTENT_TYPE)
                    .accept(JSON_CONTENT_TYPE)
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
                        .content(toJson(new User(null, "testThatUserIsFound", "test@test.com"), converter))
                        .contentType(JSON_CONTENT_TYPE)
                        .accept(JSON_CONTENT_TYPE)
        ).andExpect(status().isCreated())
        .andReturn().getResponse().getHeader("location");

        String userId = location.substring(location.lastIndexOf("/") + 1);
        this.mockMvc.perform(
                get("/users/" + userId)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("testThatUserIsFound")));
    }

    @Test
    public void testThatUserIsRejectWithInvalidData() throws Exception {
        this.mockMvc.perform(
                post("/users")
                        .content(toJson(new User(null, null, "testtest.com"), converter))
                        .contentType(JSON_CONTENT_TYPE)
                        .accept(JSON_CONTENT_TYPE)
        ).andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[*].field", containsInAnyOrder("name", "email")))
        .andExpect(jsonPath("$[*].errorMessage", containsInAnyOrder("must not be blank","must be a well-formed email address")));
    }
}
