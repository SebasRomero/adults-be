package com.videos_be.adults.category.repository;

import com.videos_be.adults.category.model.CategoryModel;
import com.videos_be.adults.video.model.VideoModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<CategoryModel, String> {

    Optional<CategoryModel> existsByName(String name);


}