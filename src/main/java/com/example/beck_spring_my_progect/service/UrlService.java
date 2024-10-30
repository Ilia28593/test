package com.example.beck_spring_my_progect.service;

import com.example.beck_spring_my_progect.model.UrlMapping;
import com.example.beck_spring_my_progect.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    public String shortenUrl(UrlMapping originalUrl) {
        Optional<UrlMapping> byOriginalUrl = urlRepository.findByOriginalUrl(originalUrl.getOriginalUrl());
        if (byOriginalUrl.isEmpty()) {
            String encodeToString = Base64.getUrlEncoder().encodeToString(originalUrl.getOriginalUrl().getBytes(StandardCharsets.UTF_8));
            String shortUrl = encodeToString.substring(encodeToString.length()-12, encodeToString.length());
            originalUrl.setShortUrl(shortUrl);
            byOriginalUrl = Optional.of(urlRepository.save(originalUrl));
        }
        return byOriginalUrl.get().getShortUrl();
    }


    public String getOriginalUrl(String shortUrl) {
        UrlMapping urlMapping = urlRepository.findByShortUrl(shortUrl);
        return (urlMapping != null) ? urlMapping.getOriginalUrl() : null;
    }
}
