package com.cognitivecare4u.cognitiveapi.images;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    private String id;

    @ReadOnlyProperty
    private MultipartFile file;

    @JsonIgnore
    private String originalPath;

    public Image(MultipartFile file) {
        this.file = file;
    }
}
