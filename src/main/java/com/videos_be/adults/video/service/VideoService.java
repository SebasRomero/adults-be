package com.videos_be.adults.video.service;

import com.videos_be.adults.category.model.CategoryModel;
import com.videos_be.adults.category.repository.CategoryRepository;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VideoService {



    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public VideoModel addVideo(CreateVideoDto createVideoDto) {
            VideoModel video = new VideoModel(null,createVideoDto.getName(), createVideoDto.getCategories(),
                    createVideoDto.getActressName(), 0);
        VideoModel newVideo;
        try {
            Optional<VideoModel> videoFound = this.videoRepository.findByNameLike(video.getName());
            List<CategoryModel> categotyList = this.categoryRepository.findAll();
            List<String> category = video.getCategories();
            List<String> categoryNames = categotyList.stream()
                    .map(CategoryModel::getName)
                    .toList();
            if (videoFound.isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Video already exist.", null);
            else if (!CollectionUtils.containsAny(category, categoryNames)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category doesn't exist.", null);
            }
            newVideo = this.videoRepository.save(video);
        } catch (Error error) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, error.getMessage(), null);
        }
        return newVideo;
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
