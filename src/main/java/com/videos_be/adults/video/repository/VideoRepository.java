package com.videos_be.adults.video.repository;

import java.util.List;

import com.videos_be.adults.video.model.VideoModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<VideoModel, Long> {
    //List<Tutorial> findByTitleContaining(String title);
    //List<Tutorial> findByPublished(boolean published);
}