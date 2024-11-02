package com.example.beck_spring_my_progect;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.beck_spring_my_progect.controller.request.RequesURLtDto;
import com.example.beck_spring_my_progect.exception.NotFoundOriginalUrlException;
import com.example.beck_spring_my_progect.model.URLModel;
import com.example.beck_spring_my_progect.repository.UrlRepository;
import com.example.beck_spring_my_progect.service.RandomStringGenerator;
import com.example.beck_spring_my_progect.service.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private RandomStringGenerator stringGenerator;

    @InjectMocks
    private UrlService urlService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShortenUrl_NewUrl() {
        // Arrange
        String originalUrl = "http://example.com";
        String generatedShortUrl = "abcd1234";

        RequesURLtDto request = new RequesURLtDto();
        request.setOriginalUrl(originalUrl);

        when(urlRepository.findByOriginalUrl(originalUrl)).thenReturn(Optional.empty());
        when(stringGenerator.generateRandomString(8)).thenReturn(generatedShortUrl);
        when(urlRepository.existsByShortUrl(generatedShortUrl)).thenReturn(false);
        when(urlRepository.save(any(URLModel.class))).thenAnswer(invocation -> {
            URLModel urlModel = invocation.getArgument(0);
            urlModel.setShortUrl(generatedShortUrl);
            return urlModel;
        });

        // Act
        String shortUrl = urlService.shortenUrl(request);

        // Assert
        assertEquals(generatedShortUrl, shortUrl);
        verify(urlRepository).findByOriginalUrl(originalUrl);
        verify(urlRepository).save(any(URLModel.class));
    }

    @Test
    void testShortenUrl_ExistingUrl() {
        // Arrange
        String originalUrl = "http://example.com";
        String existingShortUrl = "xyz789";

        RequesURLtDto request = new RequesURLtDto();
        request.setOriginalUrl(originalUrl);

        URLModel existingUrlModel = new URLModel();
        existingUrlModel.setShortUrl(existingShortUrl);

        when(urlRepository.findByOriginalUrl(originalUrl)).thenReturn(Optional.of(existingUrlModel));

        // Act
        String shortUrl = urlService.shortenUrl(request);

        // Assert
        assertEquals(existingShortUrl, shortUrl);
        verify(urlRepository).findByOriginalUrl(originalUrl);
        verify(urlRepository, never()).save(any(URLModel.class));
    }

    @Test
    void testGetOriginalUrl_ExistingShortUrl() {
        // Arrange
        String shortUrl = "abcd1234";
        String originalUrl = "http://example.com";

        URLModel urlModel = new URLModel();
        urlModel.setOriginalUrl(originalUrl);

        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.of(urlModel));

        // Act
        String result = urlService.getOriginalUrl(shortUrl);

        // Assert
        assertEquals(originalUrl, result);
        verify(urlRepository).findByShortUrl(shortUrl);
    }

    @Test
    void testGetOriginalUrl_NonExistingShortUrl() {
        // Arrange
        String shortUrl = "nonexisting";

        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundOriginalUrlException.class, () -> {
            urlService.getOriginalUrl(shortUrl);
        });

        verify(urlRepository).findByShortUrl(shortUrl);
    }
}
