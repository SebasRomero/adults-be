package com.videos_be.adults.video.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class UpdateVideoDto {
        @NotEmpty(message = "Name is required")
        private String name;
        private List<String> categories;
        private List<String> actressName;
        private int viewed;

}
