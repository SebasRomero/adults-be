package com.videos_be.adults.video.service;

import com.videos_be.adults.video.dto.CreateVideoDto;
import com.videos_be.adults.video.dto.UpdateVideoDto;
import com.videos_be.adults.video.model.VideoModel;
import com.videos_be.adults.video.repository.VideoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public class VideoService{

    private VideoRepository videoRepository;

    public ResponseEntity<VideoModel> addVideo(CreateVideoDto createVideoDto) {
        VideoModel video = new VideoModel(createVideoDto.getName(), createVideoDto.getCategories(),
                createVideoDto.getActressName(), createVideoDto.getViewed());
        return new ResponseEntity<>(video, null, HttpStatus.CREATED);
    }

    public ResponseEntity<List<VideoModel>>  getAllVideos() {
        List<VideoModel> video = videoRepository.findAll();
        return new ResponseEntity<>(video, null, HttpStatus.OK);
    }

    public ResponseEntity<Optional<VideoModel>> getById(String id) {
        Optional<VideoModel> video = this.videoRepository.findById(id);
        if(video.isPresent()){
            return new ResponseEntity<>(video, null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, null, HttpStatus.OK);
    }

    public ResponseEntity<Optional<VideoModel>> getByName(String name) {
        Optional<VideoModel> videoName = this.videoRepository.existsByName(name);
        if(videoName.isPresent()){
            return new ResponseEntity<>(videoName, null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, null, HttpStatus.OK);
    }

    public ResponseEntity<VideoModel> updateVideo(String id, UpdateVideoDto updateVideoDto) {
        String name = updateVideoDto.getName();
        List<String> categories = updateVideoDto.getCategories();
        List<String> actressName = updateVideoDto.getActressName();
        int viewed = updateVideoDto.getViewed();
        Optional<VideoModel> video = this.videoRepository.findById(id);

        if(video.isPresent()){
            VideoModel currentVideo = video.get();
            currentVideo.setName(name);
            currentVideo.setCategories(categories != null ? categories : currentVideo.getCategories());
            currentVideo.setActreesName(actressName != null ? actressName : currentVideo.getActreesName());
            currentVideo.setViewed(viewed != currentVideo.getViewed() ? viewed : currentVideo.getViewed());
            this.videoRepository.save(currentVideo);
            return new ResponseEntity<>(currentVideo, null, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<VideoModel> deleteVideo(String id) {

        Optional<VideoModel> video = this.videoRepository.findById(id);
        if(video.isPresent()){
            this.videoRepository.deleteById(id);
            return new ResponseEntity<>(video.get(), null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);

    }
}
