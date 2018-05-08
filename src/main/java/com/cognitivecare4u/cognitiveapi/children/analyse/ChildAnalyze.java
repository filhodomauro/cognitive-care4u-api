package com.cognitivecare4u.cognitiveapi.children.analyse;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChildAnalyze {
    private float profanity;
    private float bullying;
    private float sexualContent;
    private float happy;
    private float sad;
}
