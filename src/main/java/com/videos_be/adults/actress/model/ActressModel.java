package com.videos_be.adults.actress.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
}
