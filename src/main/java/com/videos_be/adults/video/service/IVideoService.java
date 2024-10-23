package com.videos_be.adults.video.service;
import com.videos_be.adults.video.model.VideoModel;
import java.util.List;

public interface IVideoService {
    VideoModel addVideo(VideoModel video);
    List<VideoModel> getAllVideos();
    VideoModel getById(Long id);
    VideoModel getByName(String name);
    void updateVideo();
    void deleteVideo();




}
