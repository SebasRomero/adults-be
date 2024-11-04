package com.videos_be.adults.video.repository;

import java.util.List;
import java.util.Optional;

import com.videos_be.adults.video.model.VideoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;


public interface VideoRepository extends MongoRepository<VideoModel, String> {

    Page<VideoModel> findAllByNameLike(String name, Pageable pageable);

    //List<Tutorial> findByTitleContaining(String title);
    //List<Tutorial> findByPublished(boolean published);
}