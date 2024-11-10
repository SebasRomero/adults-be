package com.videos_be.adults.actress.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class CreateActressDto {
    @NotEmpty(message = "Name is required")
    private String name;
    @NotNull(message = "Birth is required")
    private Date birth;
    @NotEmpty(message = "Genre is required")
    private String genre;
    @NotEmpty(message = "Nationality is required")
    private String nationality;
    @NotEmpty(message = "Categories are required")
    private List<String> categories;
}
