package com.videos_be.adults.actress.repository;

import com.videos_be.adults.actress.model.ActressModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActressRepository extends MongoRepository<ActressModel, String> {

}