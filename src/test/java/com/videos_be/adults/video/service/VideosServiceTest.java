package com.videos_be.adults.video.service;

import com.videos_be.adults.actress.model.ActressModel;
import com.videos_be.adults.category.service.CategoryService;
import com.videos_be.adults.video.DataVideoProvider;
import com.videos_be.adults.video.dto.CreateVideoDto;
import com.videos_be.adults.video.model.VideoModel;
import com.videos_be.adults.video.repository.VideoRepository;
import com.videos_be.adults.video.service.VideoService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class VideosServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private VideoService videoService;

    @Test
    public void getAllVideoNameNullTest() {
        Pageable pageable = (Pageable) PageRequest.of(0, 8);
        when(videoRepository.findAll(pageable)).thenReturn(DataVideoProvider.findAllVideoMock(pageable));

        Page<VideoModel> videoModels = this.videoService.getAllVideos(pageable, null);
        assertNotNull(videoModels.getContent());
        assertFalse(videoModels.getContent().isEmpty());
        assertEquals("Fake taxi", videoModels.getContent().get(0).getName());
        verify(this.videoRepository).findAll(pageable);
    }


    @Test
    public void getAllVideoNameNotNullTest() {
        Pageable pageable = (Pageable) PageRequest.of(0, 8);
        when(videoRepository.findAllByNameLike("Fake taxi", pageable)).thenReturn(DataVideoProvider.findOneVideoMock(pageable));

        Page<VideoModel> videoModels = this.videoService.getAllVideos(pageable, "Fake taxi");
        assertNotNull(videoModels.getContent());
        assertFalse(videoModels.getContent().isEmpty());
        assertEquals("Fake taxi", videoModels.getContent().get(0).getName());
        verify(this.videoRepository).findAllByNameLike("Fake taxi", pageable);
    }

    @Test
    public void createVideoTest() {

        when(videoRepository.save(any(VideoModel.class)))
                .thenReturn(DataVideoProvider.createVideoMock());
       when(categoryService.allCategoriesExistInDB(anyList())).thenReturn(true);

        CreateVideoDto videoDto = DataVideoProvider.createVideoDtoMock();
        VideoModel videoModel = this.videoService.addVideo(videoDto);

        assertFalse(videoModel.getName().isEmpty());
        assertEquals("Shopping Spree", videoModel.getName());
        verify(videoRepository).save(any(VideoModel.class));
        ArgumentCaptor<VideoModel> argumentCaptor = ArgumentCaptor.forClass(VideoModel.class);
        verify(videoRepository).save(argumentCaptor.capture());
        assertEquals(videoModel.getName(), argumentCaptor.getValue().getName());
    }


}
