package com.videos_be.adults.actress.controller;

import com.videos_be.adults.actress.dto.CreateActressDto;
import com.videos_be.adults.actress.dto.UpdateActressDto;
import com.videos_be.adults.actress.service.ActressService;
import com.videos_be.adults.handler.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    ResponseEntity<Object> getAllActresses(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int sizePerPage) {
        Pageable pageable = (Pageable) PageRequest.of(page, sizePerPage);
        return ResponseHandler.generateResponse("Actresses", HttpStatus.OK, this.actressService.getAllActresses(pageable));
    }

    @GetMapping("{id}")
    ResponseEntity<Object> getActressById(@PathVariable("id") String id) {
        return ResponseHandler.generateResponse("Actress", HttpStatus.OK, this.actressService.getActressById(id));
    }

    @PostMapping()
    ResponseEntity<Object> saveActress(@Validated @RequestBody CreateActressDto createActressDto){
        return ResponseHandler.generateResponse("Actress saved", HttpStatus.CREATED, this.actressService.saveActress(createActressDto));
    }

    @DeleteMapping("{id}")
    ResponseEntity<Object> deleteActress(@PathVariable("id") String id) {
        return ResponseHandler.generateResponse("Actress deleted", HttpStatus.OK, this.actressService.deleteActress((id)));
}

    @PutMapping("{id}")
    ResponseEntity<Object> updateActress(@PathVariable("id") String id, @RequestBody UpdateActressDto updateActressDto) {
        return ResponseHandler.generateResponse("Actress updated", HttpStatus.OK, this.actressService.updateActress(id, updateActressDto));
}

}
