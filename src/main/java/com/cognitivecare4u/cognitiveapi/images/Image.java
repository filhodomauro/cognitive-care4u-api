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

    @ReadOnlyProperty
    MultipartFile file;

    public Image(MultipartFile file) {
        this.file = file;
    }
}