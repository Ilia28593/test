package com.example.beck_spring_my_progect.repository;

import com.example.beck_spring_my_progect.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<UrlMapping, Long> {

    UrlMapping findByShortUrl(String shortUrl);

    Optional<UrlMapping> findByOriginalUrl(String originalUrl);
}
