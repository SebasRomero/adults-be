package com.videos_be.adults.video.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class CreateVideoDto {
    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Categories are required")
    private List<String> categories;
    @NotEmpty(message = "Actress are required")
    private List<String> actressName;
}



