package com.example.beck_spring_my_progect.service;

import com.example.beck_spring_my_progect.controller.request.RequesURLtDto;
import com.example.beck_spring_my_progect.exception.NotFoundOriginalUrlException;
import com.example.beck_spring_my_progect.model.URLModel;
import com.example.beck_spring_my_progect.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final RandomStringGenerator stringGenerator;

    public String shortenUrl(@NotNull RequesURLtDto requesURLtDto) {
        Optional<URLModel> byOriginalUrl = urlRepository.findByOriginalUrl(requesURLtDto.getOriginalUrl());
        if (byOriginalUrl.isEmpty()) {
            String shortUrl = null;
            while (urlRepository.existsByShortUrl(shortUrl) || shortUrl == null) {
                shortUrl = stringGenerator.generateRandomString(8);
            }
            URLModel originalUrl = new URLModel();
            originalUrl.setShortUrl(shortUrl);
            byOriginalUrl = Optional.of(urlRepository.save(originalUrl));
        }
        return byOriginalUrl.get().getShortUrl();
    }


    public String getOriginalUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl)
                .map(URLModel::getOriginalUrl)
                .orElseThrow(() -> new NotFoundOriginalUrlException(shortUrl));
    }
}
