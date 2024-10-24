package com.videos_be.adults.actress.controller;

import com.videos_be.adults.actress.dto.ActressDto;
import com.videos_be.adults.actress.service.ActressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/actress")
public class ActressController {

    @Autowired
    private ActressService actressService;
    @GetMapping()
    String getAllActresses() {
        return "Actresses";
    }

    @PostMapping()
    String saveActress(@Validated @RequestBody ActressDto actressDto){
        System.out.println(actressDto);
        return actressService.saveActress(actressDto);
    }
}
