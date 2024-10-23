package com.videos_be.adults.actress.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/actress")
public class ActressController {

    @GetMapping()
    String getAllActreeses() {
        return "Actresses";
    }
}
