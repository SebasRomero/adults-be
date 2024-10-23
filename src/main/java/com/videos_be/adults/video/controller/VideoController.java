package com.videos_be.adults.video.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/video")
public class VideoController {

    @GetMapping()
    String getAllVideos() {
       return "Videos";
    }
}
