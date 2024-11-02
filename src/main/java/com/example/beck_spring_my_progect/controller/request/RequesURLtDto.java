package com.example.beck_spring_my_progect.controller.request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RequesURLtDto {
    @Pattern(regexp = "[^(http|https):////]")
    private String originalUrl;
}
