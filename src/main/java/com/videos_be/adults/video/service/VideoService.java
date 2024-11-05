package com.videos_be.adults.video.service;

import com.videos_be.adults.video.dto.CreateVideoDto;
import com.videos_be.adults.video.dto.UpdateVideoDto;
import com.videos_be.adults.video.model.VideoModel;
import com.videos_be.adults.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public VideoModel addVideo(CreateVideoDto createVideoDto) {
        VideoModel video = new VideoModel(null,createVideoDto.getName(), createVideoDto.getCategories(),
                createVideoDto.getActressName(), 0);
        try {
            return this.videoRepository.save(video);
        } catch (Error error) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, error.getMessage(), null);
        }
    }

        public Page<VideoModel> getAllVideos(Pageable pageable, String name) {
        if(name != null) return this.videoRepository.findAllByNameLike(name, pageable);
        return this.videoRepository.findAll(pageable);
    }

    public VideoModel getById(String id) {
        try {
            Optional<VideoModel> video = this.videoRepository.findById(id);
            if (video.isPresent()) return video.get();
        } catch (Error error) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, error.getMessage(), null);
        }
        return null;
    }




    public VideoModel updateVideo(String id, UpdateVideoDto updateVideoDto) {
        String name = updateVideoDto.getName();
        List<String> categories = updateVideoDto.getCategories();
        List<String> actressName = updateVideoDto.getActressName();
        Optional<VideoModel> video = this.videoRepository.findById(id);

        if (video.isPresent()) {
            VideoModel currentVideo = video.get();
            currentVideo.setName(name);
            currentVideo.setCategories(categories != null ? categories : currentVideo.getCategories());
            currentVideo.setActressName(actressName != null ? actressName : currentVideo.getActressName());
            try {
         return   this.videoRepository.save(currentVideo);

            } catch (Error error) {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, error.getMessage(), null);
            }
        }


        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not update the video", null);
    }

    public VideoModel deleteVideo(String id) {

        Optional<VideoModel> video = this.videoRepository.findById(id);
        if (video.isPresent()) {
            try {
                this.videoRepository.deleteById(id);
            } catch (Error error) {

                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, error.getMessage(), null);
            }
            return video.get();
        }
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not delete the video", null);

    }
}
