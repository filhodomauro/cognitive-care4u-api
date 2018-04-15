package com.cognitivecare4u.cognitiveapi.children.images.storage;

import com.cloudinary.Cloudinary;
import com.cognitivecare4u.cognitiveapi.exceptions.UnprocessableEntityException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CloudinaryImageStorage implements ImageStorage {

    private final Cloudinary CLOUDINARY;
    private final RestTemplate restTemplate;

    private CloudinaryImageStorage(Cloudinary cloudinary) {
        this.restTemplate = new RestTemplateBuilder().build();
        this.CLOUDINARY = cloudinary;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String addImage(InputStream inputStream) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("type", "upload");
        String url;
        try {
            Map<String, String>response = CLOUDINARY.uploader().upload(inputStream, parameters);
            url = response.get("url");
        } catch (IOException e) {
            throw new UnprocessableEntityException("Invalid image");
        }
        return url;
    }

    @Override
    public InputStream getImage(String path) {
        return restTemplate.getForObject(path, InputStream.class);
    }

    public static ImageStorage getInstance(String cloudinaryUrl) {
        return new CloudinaryImageStorage(new Cloudinary(cloudinaryUrl));
    }
}
