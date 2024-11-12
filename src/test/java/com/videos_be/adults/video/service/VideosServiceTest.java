package com.videos_be.adults.video.service;

import com.videos_be.adults.actress.DataActressServiceProvider;
import com.videos_be.adults.actress.model.ActressModel;
import com.videos_be.adults.category.service.CategoryService;
import com.videos_be.adults.handler.ResponseHandler;
import com.videos_be.adults.video.DataVideoProvider;
import com.videos_be.adults.video.dto.CreateVideoDto;
import com.videos_be.adults.video.dto.UpdateVideoDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.print.DocFlavor;
import javax.xml.crypto.Data;

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
        when(videoRepository.findByNameLike(anyString())).thenReturn(Optional.empty());

        CreateVideoDto videoDto = DataVideoProvider.createVideoDtoMock();
        VideoModel videoModel = this.videoService.addVideo(videoDto);


        assertFalse(videoModel.getName().isEmpty());
        assertEquals("Shopping Spree", videoModel.getName());
        verify(videoRepository).save(any(VideoModel.class));
        ArgumentCaptor<VideoModel> argumentCaptor = ArgumentCaptor.forClass(VideoModel.class);
        verify(videoRepository).save(argumentCaptor.capture());
        assertEquals(videoModel.getName(), argumentCaptor.getValue().getName());
    }

    @Test
    public void createVideoTestError() {
        when(videoRepository.save(any(VideoModel.class)))
                .thenThrow(new Error("Error"));
        CreateVideoDto videoDto = DataVideoProvider.createVideoDtoMock();
        when(categoryService.allCategoriesExistInDB(anyList())).thenReturn(true);
        when(videoRepository.findByNameLike(anyString())).thenReturn(Optional.empty());

        ResponseStatusException response = assertThrows(ResponseStatusException.class,
                () -> videoService.addVideo(videoDto));

        assertEquals("Error",response.getReason());
        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());

    }

    @Test
    public void createVideoFoundTest() {
        CreateVideoDto videoDto = DataVideoProvider.createVideoDtoMock();
        when(videoRepository.findByNameLike(anyString())).thenReturn(DataVideoProvider.createVideoFoundMock());


        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                ()-> this.videoService.addVideo(videoDto)
        );

        assertEquals(exception.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(exception.getMessage(), "400 BAD_REQUEST \"Video already exist.\"");

    }

    @Test
    public void saveVideoCategoriesNotFoundTest() {
        CreateVideoDto videoDto = DataVideoProvider.createVideoDtoMock();
        when(categoryService.allCategoriesExistInDB(anyList())).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                ()-> this.videoService.addVideo(videoDto));



        assertEquals(exception.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(exception.getMessage(), "400 BAD_REQUEST \"Category doesn't exist.\"");

    }

    @Test
    public void getVideoByIdTest() {
        VideoModel video = DataVideoProvider.findByIdMock();
        when(videoRepository.findById(any(String.class))).thenReturn(Optional.of(video));
        VideoModel response = this.videoService.getById("1");
        assertEquals(response.getId(), "6");
        assertEquals(response.getName(), video.getName());
    }

    @Test
    public void getVideoByIdActressNull() {
        when(videoRepository.findById(any())).thenReturn(Optional.empty());
        VideoModel response = this.videoService.getById("1");
        assertNull(response);

    }

    @Test
    public void getVideoByIdError() {
        when(videoRepository.findById(any(String.class))).thenThrow(new Error("Error"));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> videoService.getById("%")
        );

        assertEquals(HttpStatus.BAD_GATEWAY, exception.getStatusCode());
        assertEquals("Error", exception.getReason());
    }

    @Test
    public void updateVideoTest(){
        VideoModel video = DataVideoProvider.findByIdMock();
        when(videoRepository.findById(any(String.class))).thenReturn(Optional.of(video));
        when(videoRepository.save(any(VideoModel.class))).thenReturn(DataVideoProvider.findByIdMock());
        UpdateVideoDto updateVideoDto = DataVideoProvider.updateVideoDtoMock();

        VideoModel newVideo = this.videoService.updateVideo("6", updateVideoDto);


        assertNotNull(newVideo);
        assertEquals(updateVideoDto.getName(), newVideo.getName());
        verify(videoRepository).save(any(VideoModel.class));
        ArgumentCaptor<VideoModel> argumentCaptor = ArgumentCaptor.forClass(VideoModel.class);
        verify(videoRepository).save(argumentCaptor.capture());
        assertEquals(video.getName(), argumentCaptor.getValue().getName());
    }

    @Test
    public void updateVideoPartialTest(){
        VideoModel video = DataVideoProvider.findByIdMock();
        when(videoRepository.findById(any(String.class))).thenReturn(Optional.of(video));
        when(videoRepository.save(any(VideoModel.class))).thenReturn(DataVideoProvider.findByIdMock());
        UpdateVideoDto updateVideoDto = DataVideoProvider.updateVideoDtoPartialMock();

        VideoModel newVideo = this.videoService.updateVideo("6", updateVideoDto);


        assertNotNull(newVideo);
        assertEquals(updateVideoDto.getName(), newVideo.getName());
        verify(videoRepository).save(any(VideoModel.class));
        ArgumentCaptor<VideoModel> argumentCaptor = ArgumentCaptor.forClass(VideoModel.class);
        verify(videoRepository).save(argumentCaptor.capture());
        assertEquals(video.getName(), argumentCaptor.getValue().getName());
    }


    @Test
    public void updateVideoTestError(){

        UpdateVideoDto video = DataVideoProvider.updateVideoDtoMock();
        VideoModel findVideo = DataVideoProvider.findByIdMock();
        when(videoRepository.save(any(VideoModel.class))).thenThrow(new Error("Error"));
        when(videoRepository.findById(any(String.class))).thenReturn(Optional.of(findVideo));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> videoService.updateVideo("%", video));

        assertEquals(HttpStatus.BAD_GATEWAY, exception.getStatusCode());
        assertEquals("Error", exception.getReason());


    }

    @Test
    public void updateVideoTestCatchError(){

        UpdateVideoDto updateVideoDto = DataVideoProvider.updateVideoDtoMock();


        when(videoRepository.findById(any(String.class))).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> videoService.updateVideo("%", updateVideoDto));

        assertEquals(HttpStatus.BAD_GATEWAY, exception.getStatusCode());
        assertEquals("Could not update the video", exception.getReason());
    }

    @Test
    public void deleteVideoTest(){

        VideoModel findVideo = DataVideoProvider.findByIdMock();
        when(videoRepository.findById(any(String.class))).thenReturn(Optional.of(findVideo));
        VideoModel video = this.videoService.deleteVideo("%");

        assertEquals(DataVideoProvider.findByIdMock().getName(), video.getName());
    }

    @Test
    public void deleteVideoTestErrorCatch(){

        doThrow(new Error("Error")).when(videoRepository).deleteById(any(String.class));
        VideoModel findVideo = DataVideoProvider.findByIdMock();
        when(videoRepository.findById(any(String.class))).thenReturn(Optional.of(findVideo));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> this.videoService.deleteVideo("%"));

        assertEquals(HttpStatus.BAD_GATEWAY, exception.getStatusCode());

    }

    @Test
    public void deleteVideoTestError(){

        when(videoRepository.findById(any(String.class))).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> this.videoService.deleteVideo("%"));

        assertEquals(exception.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(exception.getReason(), "Could not delete the video");

    }
}
