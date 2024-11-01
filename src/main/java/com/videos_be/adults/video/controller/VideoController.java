package com.videos_be.adults.video.controller;

import com.videos_be.adults.actress.model.ActressModel;
import com.videos_be.adults.handler.ResponseHandler;
import com.videos_be.adults.video.dto.CreateVideoDto;
import com.videos_be.adults.video.dto.UpdateVideoDto;
import com.videos_be.adults.video.model.VideoModel;
import com.videos_be.adults.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/video")
public class VideoController {

    @Autowired
    VideoService videoService;

    @GetMapping()
    public ResponseEntity<Object> getAllVideos() {
        return ResponseHandler.generateResponse(
                "Videos", HttpStatus.OK,
                this.videoService.getAllVideos());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getOne(@PathVariable("id") String id){
        ResponseEntity<Optional<VideoModel>> response = this.videoService.getById(id);
        if (response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND))
            return ResponseHandler.generateResponse(
                "Video not found", HttpStatus.NOT_FOUND,
                    response);
        return ResponseHandler.generateResponse("Video found", HttpStatus.OK, response);
    }


    @PostMapping()
    public ResponseEntity<Object> saveVideos(@Validated @RequestBody CreateVideoDto createVideoDto){
       return ResponseHandler.generateResponse(
               "Video saved", HttpStatus.OK,
               this.videoService.addVideo(createVideoDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteVideos(@PathVariable("id") String id){
        ResponseEntity<VideoModel> response = this.videoService.deleteVideo(id);
        if (response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST))
            return ResponseHandler.generateResponse("Could not delete video", HttpStatus.BAD_REQUEST, response);
        return ResponseHandler.generateResponse("Video deleted", HttpStatus.OK, response);

    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateVideos(@PathVariable("id") String id, @RequestBody UpdateVideoDto updateVideoDto){
        ResponseEntity<VideoModel> response = this.videoService.updateVideo(id, updateVideoDto);
        if (response.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST))
            return ResponseHandler.generateResponse("Could not update video", HttpStatus.BAD_REQUEST, response);
        return ResponseHandler.generateResponse("Video updated", HttpStatus.OK, response);

    }
}
