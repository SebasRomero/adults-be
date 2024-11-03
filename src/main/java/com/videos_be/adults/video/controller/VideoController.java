package com.videos_be.adults.video.controller;

import com.videos_be.adults.handler.ResponseHandler;
import com.videos_be.adults.video.dto.CreateVideoDto;
import com.videos_be.adults.video.dto.UpdateVideoDto;
import com.videos_be.adults.video.model.VideoModel;
import com.videos_be.adults.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController()
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping()
    public ResponseEntity<Object> getAllVideos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int sizePerPage) {

        Pageable pageable = (Pageable) PageRequest.of(page, sizePerPage);
        return ResponseHandler.generateResponse(
                "Videos", HttpStatus.OK,
                this.videoService.getAllVideos(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getOne(@PathVariable("id") String id){
        return ResponseHandler.generateResponse("Video", HttpStatus.OK, this.videoService.getById(id));
    }


    @PostMapping()
    public ResponseEntity<Object> saveVideos(@Validated @RequestBody CreateVideoDto createVideoDto){
       return ResponseHandler.generateResponse(
               "Video saved", HttpStatus.OK,
               this.videoService.addVideo(createVideoDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteVideos(@PathVariable("id") String id){
        return ResponseHandler.generateResponse("Video deleted", HttpStatus.OK, this.videoService.deleteVideo(id));

    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateVideos(@PathVariable("id") String id, @RequestBody UpdateVideoDto updateVideoDto){
        return ResponseHandler.generateResponse("Video updated", HttpStatus.OK, this.videoService.updateVideo(id, updateVideoDto));

    }
}
