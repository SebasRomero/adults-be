package com.videos_be.adults.actress.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EnableMongoAuditing()
@Document(collection = "actress")
public class ActressModel {
    @Id()
    private String id;
    private String name;
    private Date birth;
    private Integer videosQuantity;
    private String genre;
    private String nationality;
    private List<String> categories;
    private Integer clickCount;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public ActressModel(String id, String name, Date birth, Integer videosQuantity, String genre, String nationality, List<String> categories, Integer clickCount) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.videosQuantity = videosQuantity;
        this.genre = genre;
        this.nationality = nationality;
        this.categories = categories;
        this.clickCount = clickCount;
    }
}

