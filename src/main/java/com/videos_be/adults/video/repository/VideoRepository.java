package com.videos_be.adults.video.repository;
import java.util.Optional;
import com.videos_be.adults.video.model.VideoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface VideoRepository extends MongoRepository<VideoModel, String> {

    Page<VideoModel> findAllByNameLike(String name, Pageable pageable);
    Optional<VideoModel> findByNameLike(String name);
    //List<Tutorial> findByTitleContaining(String title);
    //List<Tutorial> findByPublished(boolean published);
}