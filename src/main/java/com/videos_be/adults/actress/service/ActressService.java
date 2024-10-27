package com.videos_be.adults.actress.service;

import com.videos_be.adults.actress.dto.CreateActressDto;
import com.videos_be.adults.actress.dto.UpdateActressDto;
import com.videos_be.adults.actress.model.ActressModel;
import com.videos_be.adults.actress.repository.ActressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ActressService {

    @Autowired
    private ActressRepository actressRepository;

    public ResponseEntity<ActressModel> saveActress(CreateActressDto createActressDto) {
        ActressModel actress = new ActressModel(null, createActressDto.getName(), createActressDto.getBirth(), 0, createActressDto.getGenre(), createActressDto.getNationality(), createActressDto.getCategories(), 0);
        ActressModel newActress;
        try {
        newActress = this.actressRepository.save(actress);
        } catch (Error error) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not save actress", null);
        }
        return new ResponseEntity<>(newActress, null, HttpStatus.CREATED);
    }

    public List<ActressModel> getAllActresses() {
        List<ActressModel> actressModels = new ArrayList<>();
    return  this.actressRepository.findAll();
    }


    public ResponseEntity<ActressModel> deleteActress(String id) {
        Optional<ActressModel> actress = this.actressRepository.findById(id);
        if (actress.isPresent()) {
           this.actressRepository.deleteById(id);
            return new ResponseEntity<>(actress.get(), null, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not delete the actress", null);
    }

    public ResponseEntity<ActressModel> updateActress(String id, UpdateActressDto updateActressDto) {
        String name = updateActressDto.getName();
        List<String> categories = updateActressDto.getCategories();
        String genre = updateActressDto.getGenre();

        Optional<ActressModel> actress = this.actressRepository.findById(id);
        if (actress.isPresent()) {
            ActressModel actualActress = actress.get();
            actualActress.setName(name != null ? name : actualActress.getName());
            actualActress.setGenre(genre != null ? genre : actualActress.getGenre());
            actualActress.setCategories(categories != null ? categories : actualActress.getCategories());
            this.actressRepository.save(actualActress);
            return new ResponseEntity<>(actualActress, null, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not delete the actress", null);
    }
}
