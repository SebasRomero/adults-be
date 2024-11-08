package com.videos_be.adults.video.service;

import com.videos_be.adults.video.repository.VideoRepository;
import com.videos_be.adults.video.service.VideoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class VideosServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoService videoService;

    @Test
    void findAllVideos(){

        //when(videoRepository.findAll()).thenReturn()







    }


}
