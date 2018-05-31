package com.cognitivecare4u.cognitiveapi.images.cognitive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassifierClass {

    private String name;

    private float score;
}
