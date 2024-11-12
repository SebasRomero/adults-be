package com.videos_be.adults.actress.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdateActressDto {
    private String name;
    private List<String> categories;
    private String genre;
}
