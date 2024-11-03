package com.videos_be.adults.video.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;


@Data
public class CreateVideoDto {
    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Categories are required")
    private List<String> categories;
    @NotEmpty(message = "Actress are required")
    private List<String> actressName;
    @NotEmpty(message = "Views are required")
    private int viewed;


}