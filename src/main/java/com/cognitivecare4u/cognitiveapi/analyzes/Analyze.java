package com.cognitivecare4u.cognitiveapi.analyzes;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Analyze {
    private float profanity;
    private float bullying;
    private float sexualContent;
    private float happy;
    private float sad;
}
