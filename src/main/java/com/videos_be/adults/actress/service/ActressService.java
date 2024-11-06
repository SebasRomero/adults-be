package com.videos_be.adults.actress.service;

import com.videos_be.adults.actress.dto.CreateActressDto;
import com.videos_be.adults.actress.dto.UpdateActressDto;
import com.videos_be.adults.actress.model.ActressModel;
import com.videos_be.adults.actress.repository.ActressRepository;
import com.videos_be.adults.category.repository.CategoryRepository;
import com.videos_be.adults.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ActressService {

    @Autowired
    private ActressRepository actressRepository;

    @Autowired
    private CategoryService categoryService;

    public ActressModel saveActress(CreateActressDto createActressDto) {

        ActressModel actress = new ActressModel(null, createActressDto.getName(), createActressDto.getBirth(),
                0, createActressDto.getGenre(), createActressDto.getNationality(),
                createActressDto.getCategories(), 0);
        ActressModel newActress;
        try {
            Optional<ActressModel> actressFound = this.actressRepository.findByNameLike(actress.getName());
            if (actressFound.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Actress already exist.", null);
            } else if (!this.categoryService.allCategoriesExistInDB(createActressDto.getCategories()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Verify that the categories exist before add.", null);
            newActress = this.actressRepository.save(actress);
        } catch (Error error) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, error.getMessage(), null);
        }
        return newActress;
    }

    public Page<ActressModel> getAllActresses(Pageable pageable, String name) {
        if (name != null) return this.actressRepository.findAllByNameLike(name, pageable);
        return this.actressRepository.findAll(pageable);
    }

    public ActressModel getActressById(String id) {
        try {
            Optional<ActressModel> actress = this.actressRepository.findById(id);
            if (actress.isPresent()) return actress.get();
        } catch (Error error) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, error.getMessage(), null);
        }
        return null;
    }


    public ActressModel deleteActress(String id) {
        Optional<ActressModel> actress = this.actressRepository.findById(id);
        if (actress.isPresent()) {
            try {
                this.actressRepository.deleteById(id);
            } catch (Error error) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage(), null);
            }
            return actress.get();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not delete the actress", null);
    }

    public ActressModel updateActress(String id, UpdateActressDto updateActressDto) {
        String name = updateActressDto.getName();
        List<String> categories = updateActressDto.getCategories();
        String genre = updateActressDto.getGenre();

        Optional<ActressModel> actress = this.actressRepository.findById(id);
        if (actress.isPresent()) {
            ActressModel currentActress = actress.get();
            currentActress.setName(name != null ? name : currentActress.getName());
            currentActress.setGenre(genre != null ? genre : currentActress.getGenre());
            currentActress.setCategories(categories != null ? categories : currentActress.getCategories());
            try {
                return this.actressRepository.save(currentActress);
            } catch (Error error) {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, error.getMessage(), null);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not delete the actress", null);
    }
}
