package com.videos_be.adults.video.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "video")

public class VideoModel {
    @Id()
    private String id;
    private String name;
    private List<String> categories;
    private List<String> actressName;
    private int viewed;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public VideoModel(String id, String name, List<String> categories,
                      List<String> actressName, int viewed) {
        this.id = id;
        this.name = name;
        this.categories = categories;
        this.actressName = actressName;
        this.viewed = viewed;
    }
}


