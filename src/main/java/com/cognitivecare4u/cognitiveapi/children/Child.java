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

    public Child(@NotBlank String name, @NotEmpty List<String> parentUsersIds) {
        this.name = name;
        this.parentUsersIds = parentUsersIds;
    }

    @Id
    private String id;

    @NotBlank
    private String name;

    @NotEmpty
    private List<String> parentUsersIds;

    private List<String> images;
}
