package com.cognitivecare4u.cognitiveapi.children.images;

import com.cognitivecare4u.cognitiveapi.visual_cognition.ClassifierClass;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildImage implements Serializable {

    @Id
    private String id;

    @NotBlank
    private String childId;

    @JsonIgnore
    @ReadOnlyProperty
    private MultipartFile file;

    private String originalPath;

    private ClassifierClass classifierClass;

    public ChildImage(MultipartFile file) {
        this.file = file;
    }
}
