package com.videos_be.adults.actress.repository;

import com.videos_be.adults.actress.model.ActressModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ActressRepository extends MongoRepository<ActressModel, String> {
Page<ActressModel> findAllByNameLike(String name, Pageable pageable);
Optional<ActressModel> findByNameLike(String name);
}
