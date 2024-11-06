package com.videos_be.adults.category.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateCategoryDto {
        @NotEmpty(message = "Name is required")
        private String name;
}
