package com.congnitivecare4u.cognitiveapi.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class User {



    @Id private String id;
    @NotNull
    private String name;

    @Email
    private String email;
}
