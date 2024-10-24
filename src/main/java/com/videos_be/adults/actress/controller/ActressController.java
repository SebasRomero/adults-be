package com.videos_be.adults.actress.controller;

import com.videos_be.adults.actress.dto.ActressDto;
import com.videos_be.adults.actress.model.ActressModel;
import com.videos_be.adults.actress.service.ActressService;
import com.videos_be.adults.handler.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/actress")
public class ActressController {

    @Autowired
    private ActressService actressService;
    @GetMapping()
    ResponseEntity<Object> getAllActresses() {
        return ResponseHandler.generateResponse("Actresses", HttpStatus.OK, this.actressService.getAllActreesses());
    }

    @PostMapping()
    ResponseEntity<Object> saveActress(@Validated @RequestBody ActressDto actressDto){
        return ResponseHandler.generateResponse("Actress saved", HttpStatus.CREATED, this.actressService.saveActress(actressDto));
    }
}
