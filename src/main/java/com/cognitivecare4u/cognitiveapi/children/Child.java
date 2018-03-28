package com.cognitivecare4u.cognitiveapi.children;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Child {

    public Child(@NotBlank String name, @NotEmpty List<String> parentUsersId) {
        this.name = name;
        this.parentUsersId = parentUsersId;
    }

    @Id
    private String id;

    @NotBlank
    private String name;

    @NotEmpty
    private List<String> parentUsersId;

    private List<String> images;
}
