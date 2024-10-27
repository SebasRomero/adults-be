package com.videos_be.adults.actress.controller;

import com.videos_be.adults.actress.dto.CreateActressDto;
import com.videos_be.adults.actress.dto.UpdateActressDto;
import com.videos_be.adults.actress.model.ActressModel;
import com.videos_be.adults.actress.service.ActressService;
import com.videos_be.adults.handler.ResponseHandler;
import jakarta.validation.Valid;
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
        return ResponseHandler.generateResponse("Actresses", HttpStatus.OK, this.actressService.getAllActresses());
    }

    @PostMapping()
    ResponseEntity<Object> saveActress(@Validated @RequestBody CreateActressDto createActressDto){
        return ResponseHandler.generateResponse("Actress saved", HttpStatus.CREATED, this.actressService.saveActress(createActressDto));
    }

    @DeleteMapping("{id}")
    ResponseEntity<Object> deleteActress(@PathVariable("id") String id) {
        ResponseEntity<ActressModel> response = this.actressService.deleteActress(id);
        return ResponseHandler.generateResponse("Actress deleted", HttpStatus.OK, response);
}

    @PutMapping("{id}")
    ResponseEntity<Object> updateActress(@PathVariable("id") String id, @RequestBody UpdateActressDto updateActressDto) {
        ResponseEntity<ActressModel> response = this.actressService.updateActress(id, updateActressDto);
        return ResponseHandler.generateResponse("Actress updated", HttpStatus.OK, response);
}

}
