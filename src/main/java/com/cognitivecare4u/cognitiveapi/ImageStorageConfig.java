package com.cognitivecare4u.cognitiveapi;

import com.cognitivecare4u.cognitiveapi.children.images.storage.CloudinaryImageStorage;
import com.cognitivecare4u.cognitiveapi.children.images.storage.ImageStorage;
import com.cognitivecare4u.cognitiveapi.children.images.storage.MockImageStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Slf4j
@Configuration
public class ImageStorageConfig {

    @Value("${care4u.images.storage}")
    private String storagePath;

    @Value("${spring.profiles.active:test}")
    private String activeProfile;

    @Primary
    @Profile("production")
    @Bean
    public ImageStorage getProductionStorage() {
        return CloudinaryImageStorage.getInstance(storagePath);
    }

    @Bean
    public ImageStorage getStorage() {
        return new MockImageStorage(activeProfile);
    }
}
