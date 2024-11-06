package com.videos_be.adults.category.repository;

import com.videos_be.adults.category.model.CategoryModel;
import com.videos_be.adults.video.model.VideoModel;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends MongoRepository<CategoryModel, String> {

}