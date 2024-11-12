package com.videos_be.adults.actress.service;

import static org.junit.jupiter.api.Assertions.*;

import com.videos_be.adults.actress.DataActressServiceProvider;
import com.videos_be.adults.actress.dto.CreateActressDto;
import com.videos_be.adults.actress.dto.UpdateActressDto;
import com.videos_be.adults.actress.model.ActressModel;
import com.videos_be.adults.actress.repository.ActressRepository;
import com.videos_be.adults.category.repository.CategoryRepository;
import com.videos_be.adults.category.service.CategoryService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ActressServiceTest {

    @Mock
    private ActressRepository actressRepository;

    @InjectMocks
    private ActressService actressService;

    @Mock
    private CategoryService categoryService;
    @Test
    public void getAllActressesNameNullTest() {
        Pageable pageable = (Pageable) PageRequest.of(0, 8);
        when(actressRepository.findAll(pageable)).thenReturn(DataActressServiceProvider.actressesListMock(pageable));

        Page<ActressModel> actressModels = this.actressService.getAllActresses(pageable, null);
        assertNotNull(actressModels.getContent());
        assertFalse(actressModels.getContent().isEmpty());
        assertEquals("Federica", actressModels.getContent().get(0).getName());
        verify(this.actressRepository).findAll(pageable);
    }

    @Test
    public void getAllActressesNameNotNullTest() {
        Pageable pageable = (Pageable) PageRequest.of(0, 8);
        String name = "Paola";
        when(actressRepository.findAllByNameLike(name, pageable)).thenReturn(DataActressServiceProvider.actressesNameListMock(pageable));

        Page<ActressModel> actressModels = this.actressService.getAllActresses(pageable, name);
        assertNotNull(actressModels.getContent());
        assertFalse(actressModels.getContent().isEmpty());
        assertEquals(name, actressModels.getContent().get(0).getName());
        verify(this.actressRepository).findAllByNameLike(name, pageable);
    }

    @Test
    public void saveActressTest() {
        CreateActressDto actressModelDto = DataActressServiceProvider.newActressMockDto();
        ActressModel actressModel = DataActressServiceProvider.newActressMock();
        when(this.actressRepository.findByNameLike(anyString())).thenReturn(Optional.empty());
        when(this.categoryService.allCategoriesExistInDB(actressModel.getCategories())).thenReturn(true);
        when(this.actressRepository.save(any(ActressModel.class))).thenReturn(DataActressServiceProvider.newActressMock());


        this.actressService.saveActress(actressModelDto);

        verify(this.actressRepository).save(any(ActressModel.class));
        ArgumentCaptor<ActressModel> argumentCaptor = ArgumentCaptor.forClass(ActressModel.class);
        verify(this.actressRepository).save(argumentCaptor.capture());
        assertEquals(actressModel.getName(), argumentCaptor.getValue().getName());
    }

    @Test
    public void saveActressFoundTest() {
        CreateActressDto actressModelDto = DataActressServiceProvider.newActressMockDto();
        when(this.actressRepository.findByNameLike(anyString())).thenReturn(DataActressServiceProvider.findByNameLikeMock());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            this.actressService.saveActress(actressModelDto);
        });

        assertEquals(exception.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(exception.getMessage(), "400 BAD_REQUEST \"Actress already exist.\"");
    }

    @Test
    public void saveActressCategoriesNotFoundTest() {
        CreateActressDto actressModelDto = DataActressServiceProvider.newActressMockDto();
        when(this.actressRepository.findByNameLike(anyString())).thenReturn(Optional.empty());
        when(this.categoryService.allCategoriesExistInDB(DataActressServiceProvider.categoryModelListMock())).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> {
            this.actressService.saveActress(actressModelDto);
        });
    }

    @Test
    public void saveActressDBErrorTest() {
        CreateActressDto actressModelDto = DataActressServiceProvider.newActressMockDto();
        when(this.actressRepository.findByNameLike(anyString())).thenReturn(Optional.empty());
        when(this.categoryService.allCategoriesExistInDB(DataActressServiceProvider.categoryModelListMock())).thenReturn(true);
        when(this.actressRepository.save(any(ActressModel.class))).thenThrow(new Error("Error"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            this.actressService.saveActress(actressModelDto);
        });

        assertEquals(HttpStatus.BAD_GATEWAY, exception.getStatusCode());
        assertEquals("Error", exception.getReason());
    }

    @Test
    public void getActressByIdTest() {
        ActressModel foundActressModel = DataActressServiceProvider.foundActressModel();
        when(this.actressRepository.findById(any(String.class))).thenReturn(Optional.of(foundActressModel));
        ActressModel response = this.actressService.getActressById("1");
        assertEquals(response.getName(), foundActressModel.getName());
        assertEquals(response.getId(), "3");
    }

    @Test
    public void getActressByIdActressNullTest() {
        when(this.actressRepository.findById(any(String.class))).thenReturn(Optional.empty());
        ActressModel response = this.actressService.getActressById("1");
        assertNull(response);
    }

    @Test
    public void getActressByIdErrorTest() {
        when(this.actressRepository.findById(any(String.class))).thenThrow(new Error("Error"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            this.actressService.getActressById("1");
        });

        assertEquals(HttpStatus.BAD_GATEWAY, exception.getStatusCode());
        assertEquals("Error", exception.getReason());
    }

    @Test
    public void deleteActressTest() {
        when(this.actressRepository.findById(anyString())).thenReturn(Optional.of(DataActressServiceProvider.foundActressModel()));
        ActressModel actressModel = this.actressService.deleteActress("1");
        assertEquals(DataActressServiceProvider.foundActressModel().getName(), actressModel.getName());
    }

    @Test
    public void deleteActressNotFoundTest() {
        when(this.actressRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
         this.actressService.deleteActress("%");
        });

        assertEquals(exception.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(exception.getReason(), "Could not delete the actress");
    }

    @Test
    public void deleteActressErrorTest() {
        doThrow(new Error("Error")).when(this.actressRepository).deleteById(anyString());
        when(this.actressRepository.findById(anyString())).thenReturn(Optional.of(DataActressServiceProvider.foundActressModel()));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            this.actressService.deleteActress("%");
        });
        assertEquals(exception.getStatusCode(), HttpStatus.BAD_GATEWAY);
    }

    @Test
    public void updateActressTest() {
        UpdateActressDto updateActressDto = DataActressServiceProvider.updateActressDto();
    when(this.actressRepository.findById(anyString())).thenReturn(Optional.of(DataActressServiceProvider.foundActressModel()));
    when(this.actressRepository.save(any(ActressModel.class))).thenReturn(DataActressServiceProvider.newActressMock());

    this.actressService.updateActress("%", updateActressDto);

    verify(this.actressRepository).save(any(ActressModel.class));
    ArgumentCaptor<ActressModel> argumentCaptor = ArgumentCaptor.forClass(ActressModel.class);
    verify(this.actressRepository).save(argumentCaptor.capture());
    assertEquals(updateActressDto.getName(), argumentCaptor.getValue().getName());
    }

    @Test
    public void updateActressNotFoundTest(){
        UpdateActressDto updateActressDto = DataActressServiceProvider.updateActressDto();
        when(this.actressRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            this.actressService.updateActress("%", updateActressDto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Could not delete the actress", exception.getReason());
    }


    @Test
    public void updateActressPartialTest() {
        UpdateActressDto updateActressDto = DataActressServiceProvider.updateActressDtoPartial();
        when(this.actressRepository.findById(anyString())).thenReturn(Optional.of(DataActressServiceProvider.foundActressModel()));
        when(this.actressRepository.save(any(ActressModel.class))).thenReturn(DataActressServiceProvider.newActressMock());

        this.actressService.updateActress("%", updateActressDto);

        verify(this.actressRepository).save(any(ActressModel.class));
        ArgumentCaptor<ActressModel> argumentCaptor = ArgumentCaptor.forClass(ActressModel.class);
        verify(this.actressRepository).save(argumentCaptor.capture());
        assertNotEquals(updateActressDto.getName(), argumentCaptor.getValue().getName());
    }

    @Test
    public void updateActressErrorTest() {
        UpdateActressDto updateActressDto = DataActressServiceProvider.updateActressDto();
        when(this.actressRepository.findById(anyString())).thenReturn(Optional.of(DataActressServiceProvider.foundActressModel()));
        when(this.actressRepository.save(any(ActressModel.class))).thenThrow(new Error(("Error")));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            this.actressService.updateActress("%", updateActressDto);
        });

        assertEquals(HttpStatus.BAD_GATEWAY, exception.getStatusCode());
    }

}
