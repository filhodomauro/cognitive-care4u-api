package com.cognitivecare4u.cognitiveapi.images.storage;

import com.cloudinary.Cloudinary;
import com.cognitivecare4u.cognitiveapi.exceptions.UnprocessableEntityException;
import com.cognitivecare4u.cognitiveapi.images.ChildImage;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CloudinaryImageStorage implements ImageStorage {

    private RestTemplate restTemplate;

    public CloudinaryImageStorage() {
        restTemplate = new RestTemplateBuilder().build();
    }

    @SuppressWarnings("unchecked")
    @Override
    public String addImage(ChildImage childImage) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("type", "upload");
        String url;
        Cloudinary cloudinary = new Cloudinary();
        try {
            byte[] file = childImage.getFile().getBytes();
            Map<String, String>response = cloudinary.uploader().upload(file, parameters);
            url = response.get("url");
        } catch (IOException e) {
            throw new UnprocessableEntityException("Invalid image");
        }
        return url;
    }

    @Override
    public byte[] getImage(ChildImage childImage) {
        return restTemplate.getForObject(childImage.getOriginalPath(), byte[].class);
    }
}
