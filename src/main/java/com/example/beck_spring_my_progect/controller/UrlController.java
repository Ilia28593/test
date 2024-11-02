package com.example.beck_spring_my_progect.controller;

import com.example.beck_spring_my_progect.controller.request.RequesURLtDto;
import com.example.beck_spring_my_progect.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/url")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody RequesURLtDto requesURLtDto) {
        log.info("[POST] /shorten {}", requesURLtDto);
        return ResponseEntity.ok(urlService.shortenUrl(requesURLtDto));
    }

    @GetMapping("/{shortUrl}")
    public void redirectToOriginalUrl2(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        String originalUrl = urlService.getOriginalUrl(shortUrl);
        if (originalUrl != null) {
            response.sendRedirect(originalUrl);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

