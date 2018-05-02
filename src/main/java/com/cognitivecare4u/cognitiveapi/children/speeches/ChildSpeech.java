package com.cognitivecare4u.cognitiveapi.children.speeches;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.validation.constraints.NotBlank;
import java.io.InputStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildSpeech {

    @Id
    private String id;

    @NotBlank
    private String childId;

    @JsonIgnore
    @ReadOnlyProperty
    private InputStream audio;

    private String audioPath;

    private String contentType;
}
