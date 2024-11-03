package com.videos_be.adults.video.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class VideoModel {
    @Id()
    private String id;
    private String name;
    private List<String> categories;
    private List<String> actreesName;
    private int viewed;

}
