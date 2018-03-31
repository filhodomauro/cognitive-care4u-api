package com.cognitivecare4u.cognitiveapi;

import com.cognitivecare4u.cognitiveapi.images.storage.CloudinaryImageStorage;
import com.cognitivecare4u.cognitiveapi.images.storage.ImageStorage;
import com.cognitivecare4u.cognitiveapi.images.storage.MockImageStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ImageStorageConfig {

    @Value("${care4u.images.storage}")
    private String storagePath;

    @Profile("production")
    @Bean
    public ImageStorage getProductionStorage() {
        return new CloudinaryImageStorage();
    }

    @Bean
    public ImageStorage getStorage() {
        return new MockImageStorage();
    }
}
