package com.videos_be.adults.actress;

import com.videos_be.adults.actress.dto.CreateActressDto;
import com.videos_be.adults.actress.model.ActressModel;
import com.videos_be.adults.category.model.CategoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DataActressServiceProvider {
    public static Page<ActressModel> actressesListMock(Pageable pageable) {
        Date myDate = new Date(2001, 9, 16);
        Page<ActressModel> actressModelsPage = new PageImpl<>(List.of(new ActressModel("1", "Federica", myDate, 2, "Female", "Colombian", List.of("White woman"), 0),
                new ActressModel("2", "Armanda", myDate, 2, "Female", "Venezuelan", List.of("White woman"), 0),
                new ActressModel("3", "Paola", myDate, 2, "Female", "Russian", List.of("White woman"), 0)), pageable, 0);
        return actressModelsPage;
    }

    public static Page<ActressModel> actressesNameListMock(Pageable pageable) {
        Date myDate = new Date(2001, 9, 16);
        return new PageImpl<>(List.of(new ActressModel("3", "Paola", myDate, 2, "Female", "Russian", List.of("White woman"), 0)), pageable, 0);
    }

    public static CreateActressDto newActressMockDto() {
        Date myDate = new Date(2001, 9, 16);
        return new CreateActressDto("Paola", myDate, "Female", "Mexican", List.of("White woman"));
    }

    public static ActressModel newActressMock() {
        Date myDate = new Date(2001, 9, 16);
        return new ActressModel("3", "Paola", myDate, 2, "Female", "Russian", List.of("White woman"), 0);
    }

    public static List<String> categoryModelListMock() {
        return List.of("White woman");
    }

    public static Optional<ActressModel> findByNameLikeMock() {
        Date myDate = new Date(2001, 9, 16);
        return Optional.of(new ActressModel("3", "Paola", myDate, 2, "Female", "Russian", List.of("White woman"), 0));
    }

    public static ActressModel foundActressModel() {
        Date myDate = new Date(2001, 9, 16);
        return new ActressModel("3", "Paola", myDate, 2, "Female", "Russian", List.of("White woman"), 0);
    }
}
