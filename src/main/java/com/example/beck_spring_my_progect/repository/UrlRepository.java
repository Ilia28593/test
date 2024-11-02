package com.example.beck_spring_my_progect.repository;

import com.example.beck_spring_my_progect.model.URLModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<URLModel, Long> {

    Optional<URLModel> findByShortUrl(String shortUrl);

    Optional<URLModel> findByOriginalUrl(String originalUrl);

    boolean existsByShortUrl(String shortUrl);
}
