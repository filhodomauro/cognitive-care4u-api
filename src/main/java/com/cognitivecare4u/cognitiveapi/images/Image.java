package com.cognitivecare4u.cognitiveapi.images;

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

    private String childId;

    @ReadOnlyProperty
    private MultipartFile file;

    private String originalPath;

    public Image(MultipartFile file) {
        this.file = file;
    }
}
