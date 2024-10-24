package com.videos_be.adults.actress.service;

import com.videos_be.adults.actress.dto.ActressDto;
import com.videos_be.adults.actress.model.ActressModel;
import com.videos_be.adults.actress.repository.ActressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActressService {

    @Autowired
    private ActressRepository actressRepository;

    public String saveActress(ActressDto actressDto) {
        ActressModel actress = new ActressModel(null, actressDto.getName(), actressDto.getBirth(), 0, actressDto.getGenre(), actressDto.getNationality(), actressDto.getCategories(), 0);
        this.actressRepository.save(actress);
        return "saved";
    }
}
