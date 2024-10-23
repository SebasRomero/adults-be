package com.videos_be.adults.video.service;

import com.videos_be.adults.video.exceptions.VideoNotFoundException;
import com.videos_be.adults.video.model.VideoModel;
import com.videos_be.adults.video.repository.VideoRepository;

import java.util.List;

public class VideoService implements IVideoService{

    private VideoRepository videoRepository;

    @Override
    public VideoModel addVideo(VideoModel video) {
        return null;
    }

    @Override
    public List<VideoModel> getAllVideos() {
        return videoRepository.findAll();
    }

    @Override
    public VideoModel getById(Long id) {
        return videoRepository.findById(id).orElseThrow(() -> new VideoNotFoundException("Video not found"));
    }

    @Override
    public VideoModel getByName(String name) {
        return null;
    }

    @Override
    public void updateVideo() {

    }

    @Override
    public void deleteVideo() {

    }
}
