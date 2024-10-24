package com.videos_be.adults.actress.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class UpdateActressDto {
    @NotEmpty(message = "Name is required")
    private String name;
    private List<String> categories;
}
