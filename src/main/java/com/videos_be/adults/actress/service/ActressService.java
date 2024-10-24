package com.videos_be.adults.actress.service;

import com.videos_be.adults.actress.dto.CreateActressDto;
import com.videos_be.adults.actress.dto.UpdateActressDto;
import com.videos_be.adults.actress.model.ActressModel;
import com.videos_be.adults.actress.repository.ActressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActressService {

    @Autowired
    private ActressRepository actressRepository;

    public ResponseEntity<ActressModel> saveActress(CreateActressDto createActressDto) {
        ActressModel actress = new ActressModel(null, createActressDto.getName(), createActressDto.getBirth(), 0, createActressDto.getGenre(), createActressDto.getNationality(), createActressDto.getCategories(), 0);
        ActressModel newActress = this.actressRepository.save(actress);
        return new ResponseEntity<>(newActress, null, HttpStatus.CREATED);
    }

    public ResponseEntity<List<ActressModel>> getAllActreesses() {
        List<ActressModel> actressModels = new ArrayList<>();
        actressModels = this.actressRepository.findAll();
        return new ResponseEntity<>(actressModels, null, HttpStatus.OK);
    }


    public ResponseEntity<ActressModel> deleteActress(String id) {
        Optional<ActressModel> actress = this.actressRepository.findById(id);
        if (actress.isPresent()) {
           this.actressRepository.deleteById(id);
            return new ResponseEntity<>(actress.get(), null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ActressModel> updateActress(String id, UpdateActressDto updateActressDto) {
        String name = updateActressDto.getName();
        List<String> categories = updateActressDto.getCategories();
        Optional<ActressModel> actress = this.actressRepository.findById(id);
        if (actress.isPresent()) {
            ActressModel actualActress = actress.get();
            actualActress.setName(name);
            actualActress.setCategories(categories != null ? categories : actualActress.getCategories());
            this.actressRepository.save(actualActress);
            return new ResponseEntity<>(actualActress, null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
    }
}
