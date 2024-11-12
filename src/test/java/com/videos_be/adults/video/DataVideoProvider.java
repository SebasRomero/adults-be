package com.videos_be.adults.video;

import com.videos_be.adults.video.dto.CreateVideoDto;
import com.videos_be.adults.video.dto.UpdateVideoDto;
import com.videos_be.adults.video.model.VideoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class DataVideoProvider {

    public static Page<VideoModel> findAllVideoMock(Pageable pageable){

        return new PageImpl<>(List.of(
                new VideoModel("1", "Fake taxi", List.of("White woman"), List.of("Juana"), 0),
                new VideoModel("2", "Street Interview", List.of("Asian woman"), List.of("Maria"), 1),
                new VideoModel("3", "Hotel Secrets", List.of("Black woman"), List.of("Laura"), 2),
                new VideoModel("4", "Beach Adventures", List.of("Latina"), List.of("Sofia"), 3),
                new VideoModel("5", "Road Trip", List.of("Mixed race woman"), List.of("Elena"), 4),
                new VideoModel("6", "Shopping Spree", List.of("Middle Eastern woman"), List.of("Amira"), 5)
        ));

    }

    public static Page<VideoModel> findOneVideoMock(Pageable pageable){
        return new PageImpl<>(List.of
                (new VideoModel("1", "Fake taxi", List.of("White woman"), List.of("Juana"), 0)), pageable, 0);
    }

    public static CreateVideoDto createVideoDtoMock(){
        return new CreateVideoDto("Shopping Spree",
                List.of("Middle Eastern woman"), List.of("Amira"));
    }

    public static VideoModel createVideoMock(){
        return new VideoModel("6", "Shopping Spree", List.of("Middle Eastern woman"), List.of("Amira"), 5);
    }

    public static Optional<VideoModel> createVideoFoundMock(){
        return Optional.of(new VideoModel("6", "Shopping Spree", List.of("Middle Eastern woman"), List.of("Amira"), 5));
    }

    public static VideoModel findByIdMock(){
        return new VideoModel("6", "Shopping Spree", List.of("Middle Eastern woman"), List.of("Amira"), 5);
    }

    public static UpdateVideoDto updateVideoDtoMock(){
        return new UpdateVideoDto("Shopping Spree",
                List.of("Middle Eastern woman"), List.of("Amira"));
    }

    public static UpdateVideoDto updateVideoDtoPartialMock(){
        return new UpdateVideoDto("Shopping Spree",
                null, null);
    }





}
