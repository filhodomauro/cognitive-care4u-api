package com.cognitivecare4u.cognitiveapi.utils;

import java.util.List;
import java.util.Optional;

public class CognitiveCare4UHelper {

    public static <T> Optional<T> first(List<T> collection) {
        if (collection.isEmpty()) {
            return java.util.Optional.empty();
        }
        return Optional.of(collection.get(0));
    }
}
