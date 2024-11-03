package com.videos_be.adults.video.repository;

import java.util.List;
import java.util.Optional;

import com.videos_be.adults.video.model.VideoModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<VideoModel, String> {

    Optional<VideoModel> existsByName(String name);

    //List<Tutorial> findByTitleContaining(String title);
    //List<Tutorial> findByPublished(boolean published);
}