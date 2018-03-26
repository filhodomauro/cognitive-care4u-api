package com.congnitivecare4u.cognitiveapi.children;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
public class Child {

    @Id
    private String id;

    @NotBlank
    private String name;

    @NotEmpty
    private List<String> parentUsersId;
}
