package com.cognitivecare4u.cognitiveapi.types;

public enum Classes {

    HAPPY("happy"),
    SAD("sad"),
    SEXUAL_CONTENT("sexualContent"),
    BULLYING("bullying"),
    PROFANITY("profanity"),
    NOT_FOUND("");

    private String classifierClass;

    Classes(String classifierClass) {
        this.classifierClass = classifierClass;
    }

    public String getClassifierClass() {
        return classifierClass;
    }

    public static Classes fromClassifierClass(String classifierClass) {
        for (Classes classes: Classes.values()) {
            if (classes.getClassifierClass().equals(classifierClass)) {
                return classes;
            }
        }
        return NOT_FOUND;
    }
}
