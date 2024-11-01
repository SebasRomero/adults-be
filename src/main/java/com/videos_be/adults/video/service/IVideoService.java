package com.videos_be.adults.video.service;
import com.videos_be.adults.video.model.VideoModel;
import org.springframework.http.ResponseEntity;

public interface IVideoService {
    VideoModel addVideo(VideoModel video);
    ResponseEntity getAllVideos();
    VideoModel getById(Long id);
    VideoModel getByName(String name);
    void updateVideo();
    void deleteVideo();




}
