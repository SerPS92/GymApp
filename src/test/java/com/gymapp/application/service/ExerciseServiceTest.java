package com.gymapp.application.service;

import com.gymapp.api.dto.category.response.CategoryResponse;
import com.gymapp.api.dto.exercise.request.ExerciseCreateRequest;
import com.gymapp.api.dto.exercise.request.ExerciseFilterRequest;
import com.gymapp.api.dto.exercise.request.ExerciseUpdateRequest;
import com.gymapp.api.dto.exercise.response.ExerciseResponse;
import com.gymapp.application.mapper.exercise.ExerciseMapper;
import com.gymapp.application.service.exercise.ExerciseServiceImpl;
import com.gymapp.domain.entity.Category;
import com.gymapp.domain.entity.Exercise;
import com.gymapp.domain.enums.Difficulty;
import com.gymapp.infrastructure.persistence.CategoryJpaRepository;
import com.gymapp.infrastructure.persistence.ExerciseJpaRepository;
import com.gymapp.shared.dto.PageResponseDTO;
import com.gymapp.shared.error.AppException;
import com.gymapp.shared.error.BadRequestException;
import com.gymapp.shared.error.ConflictException;
import com.gymapp.shared.error.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.gymapp.shared.error.ErrorConstants.THE_EXERCISE_WITH_ID_D_DOES_NOT_EXIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest {


    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    @Mock
    private ExerciseJpaRepository repository;

    @Mock
    private CategoryJpaRepository categoryRepository;

    @Mock
    private ExerciseMapper exerciseMapper;


    @Test
    @DisplayName("Filters by name and returns mapped page")
    void searchByNameTest() {

        Pageable pageable = PageRequest.of(0, 2);
        ExerciseFilterRequest filter = new ExerciseFilterRequest();
        filter.setName(" press ");


        Category chest = new Category(1L, "Chest");
        Category shoulders = new Category(2L, "Shoulders");

        Exercise exercise1 = Exercise.builder()
                .id(1L)
                .name("Bench Press")
                .difficulty(Difficulty.INTERMEDIATE)
                .description("desc1")
                .categories(Set.of(chest))
                .build();

        Exercise exercise2 = Exercise.builder()
                .id(2L)
                .name("Shoulder Press")
                .difficulty(Difficulty.ADVANCED)
                .description("desc2")
                .categories(Set.of(shoulders))
                .build();

        Page<Exercise> page = new PageImpl<>(List.of(exercise1, exercise2), pageable, 2);
        when(repository.findByNameContainingIgnoreCase("press", pageable)).thenReturn(page);

        ExerciseResponse response1 = ExerciseResponse.builder()
                .id(1L)
                .name("Bench Press")
                .difficulty(Difficulty.INTERMEDIATE)
                .description("desc1")
                .categories(List.of(
                        CategoryResponse.builder().id(1L).name("Chest").build()
                ))
                .build();

        ExerciseResponse response2 = ExerciseResponse.builder()
                .id(2L)
                .name("Shoulder Press")
                .difficulty(Difficulty.ADVANCED)
                .description("desc2")
                .categories(List.of(
                        CategoryResponse.builder().id(2L).name("Shoulders").build()
                ))
                .build();

        when(exerciseMapper.toResponse(exercise1)).thenReturn(response1);
        when(exerciseMapper.toResponse(exercise2)).thenReturn(response2);

        PageResponseDTO<ExerciseResponse> result = exerciseService.search(filter, pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getPage()).isZero();
        assertThat(result.getSize()).isEqualTo(2);

        ExerciseResponse first = result.getContent().get(0);
        assertThat(first.getName()).isEqualTo("Bench Press");
        assertThat(first.getDifficulty()).isEqualTo(Difficulty.INTERMEDIATE);
        assertThat(first.getCategories()).hasSize(1);
        assertThat(first.getCategories().get(0).getName()).isEqualTo("Chest");
    }


    @Test
    @DisplayName("Filters by category and returns mapped page")
    void searchByCategoryTest() {

        Pageable pageable = PageRequest.of(0, 2);
        ExerciseFilterRequest filter = new ExerciseFilterRequest();
        filter.setCategoryIds(List.of(1L));

        Category chest = new Category(1L, "Chest");

        Exercise exercise1 = Exercise.builder()
                .id(1L)
                .name("Bench Press")
                .difficulty(Difficulty.INTERMEDIATE)
                .description("Flat bench press")
                .categories(Set.of(chest))
                .build();

        Page<Exercise> page = new PageImpl<>(List.of(exercise1), pageable, 1);
        when(repository.findByCategoryIds(List.of(1L), pageable)).thenReturn(page);

        ExerciseResponse response = ExerciseResponse.builder()
                .id(1L)
                .name("Bench Press")
                .difficulty(Difficulty.INTERMEDIATE)
                .description("Flat bench press")
                .categories(List.of(CategoryResponse.builder().id(1L).name("Chest").build()))
                .build();

        when(exerciseMapper.toResponse(exercise1)).thenReturn(response);

        PageResponseDTO<ExerciseResponse> result = exerciseService.search(filter, pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getPage()).isZero();
        assertThat(result.getSize()).isEqualTo(2);

        ExerciseResponse first = result.getContent().get(0);
        assertThat(first.getName()).isEqualTo("Bench Press");
        assertThat(first.getDifficulty()).isEqualTo(Difficulty.INTERMEDIATE);
        assertThat(first.getCategories()).extracting("name").containsExactly("Chest");
    }


    @Test
    @DisplayName("Without filters uses findAll and maps to DTO")
    void searchNoFiltersUsesFindAllTest() {
        Pageable pageable = PageRequest.of(1, 3);
        ExerciseFilterRequest filter = new ExerciseFilterRequest();

        Category legs = new Category(3L, "Legs");

        Exercise e = Exercise.builder()
                .id(4L)
                .name("Squat")
                .difficulty(Difficulty.INTERMEDIATE)
                .description("Barbell back squat")
                .categories(Set.of(legs))
                .build();

        Page<Exercise> page = new PageImpl<>(List.of(e), pageable, 4);
        when(repository.findAll(pageable)).thenReturn(page);

        ExerciseResponse r = ExerciseResponse.builder()
                .id(4L)
                .name("Squat")
                .difficulty(Difficulty.INTERMEDIATE)
                .description("Barbell back squat")
                .categories(List.of(
                        CategoryResponse.builder().id(3L).name("Legs").build()
                ))
                .build();

        when(exerciseMapper.toResponse(e)).thenReturn(r);

        PageResponseDTO<ExerciseResponse> result = exerciseService.search(filter, pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getTotalElements()).isEqualTo(4);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Squat");
        assertThat(result.getContent().get(0).getDifficulty()).isEqualTo(Difficulty.INTERMEDIATE);
        assertThat(result.getContent().get(0).getCategories()).extracting("name").containsExactly("Legs");
    }


    @Test
    @DisplayName("Throws BadRequestException when both name and categoryIds are provided")
    void searchBothFiltersBadRequestTest() {
        Pageable pageable = PageRequest.of(0, 10);
        ExerciseFilterRequest filter = new ExerciseFilterRequest();
        filter.setName("press");
        filter.setCategoryIds(List.of(1L, 2L));

        assertThrows(BadRequestException.class, () -> exerciseService.search(filter, pageable));
    }


    @Test
    @DisplayName("Returns ExerciseResponse for an existing id")
    void getByIdTest() {
        Category chest = new Category(1L, "Chest");

        Exercise exercise1 = Exercise.builder()
                .id(1L)
                .name("testName")
                .difficulty(Difficulty.INTERMEDIATE)
                .description("desc")
                .categories(Set.of(chest))
                .build();

        ExerciseResponse response1 = ExerciseResponse.builder()
                .id(1L)
                .name("testName")
                .difficulty(Difficulty.INTERMEDIATE)
                .description("desc")
                .categories(List.of(
                        CategoryResponse.builder().id(1L).name("Chest").build()
                ))
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(exercise1));
        when(exerciseMapper.toResponse(exercise1)).thenReturn(response1);

        ExerciseResponse result = exerciseService.getById(1L);

        assertEquals(1L, result.getId());
        assertEquals("testName", result.getName());
        assertEquals(Difficulty.INTERMEDIATE, result.getDifficulty());
        assertThat(result.getCategories()).extracting("name").containsExactly("Chest");
    }


    @Test
    @DisplayName("Throws NotFound when id does not exist")
    void getById_notFound(){
        when(repository.findById(999L)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> exerciseService.getById(999L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(ErrorCode.NOT_FOUND, exception.getCode());
    }

    @Test
    @DisplayName("Creates exercise and returns mapped response")
    void createExerciseTest() {
        Category chest = new Category(1L, "Chest");
        List<Long> categoryIds = List.of(1L);

        ExerciseCreateRequest req = new ExerciseCreateRequest();
        req.setName("Bench Press");
        req.setDifficulty(Difficulty.INTERMEDIATE);
        req.setDescription("Classic chest press");
        req.setCategoryIds(categoryIds);

        Exercise toSave = Exercise.builder()
                .name("Bench Press")
                .difficulty(Difficulty.INTERMEDIATE)
                .description("Classic chest press")
                .categories(Set.of(chest))
                .build();

        Exercise saved = Exercise.builder()
                .id(10L)
                .name("Bench Press")
                .difficulty(Difficulty.INTERMEDIATE)
                .description("Classic chest press")
                .categories(Set.of(chest))
                .build();

        ExerciseResponse resp = ExerciseResponse.builder()
                .id(10L)
                .name("Bench Press")
                .difficulty(Difficulty.INTERMEDIATE)
                .description("Classic chest press")
                .categories(List.of(CategoryResponse.builder().id(1L).name("Chest").build()))
                .build();

        when(repository.existsByNameIgnoreCase("Bench Press")).thenReturn(false);
        when(categoryRepository.findAllById(categoryIds)).thenReturn(List.of(chest));
        when(exerciseMapper.toEntity(req)).thenReturn(toSave);
        when(repository.save(toSave)).thenReturn(saved);
        when(exerciseMapper.toResponse(saved)).thenReturn(resp);

        ExerciseResponse result = exerciseService.createExercise(req);

        assertEquals(10L, result.getId());
        assertEquals("Bench Press", result.getName());
        assertEquals(Difficulty.INTERMEDIATE, result.getDifficulty());
        assertEquals("Classic chest press", result.getDescription());
        assertThat(result.getCategories()).extracting("name").containsExactly("Chest");
    }


    @Test
    @DisplayName("Throws Conflict when exercise already exists (same name)")
    void createExercise_conflict() {
        ExerciseCreateRequest req = new ExerciseCreateRequest();
        req.setName("  Bench Press  ");
        req.setDifficulty(Difficulty.INTERMEDIATE);
        req.setDescription("dup");
        req.setCategoryIds(List.of(1L));

        when(repository.existsByNameIgnoreCase("Bench Press")).thenReturn(true);

        ConflictException ex = assertThrows(ConflictException.class,
                () -> exerciseService.createExercise(req));

        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        assertEquals(ErrorCode.CONFLICT, ex.getCode());
    }


    @Test
    @DisplayName("Updates only provided fields and returns mapped response")
    void updateExercise_partial_ok() {
        Long id = 1L;
        Category chest = new Category(1L, "Chest");

        Exercise existing = Exercise.builder()
                .id(id)
                .name("Bench Press")
                .difficulty(Difficulty.INTERMEDIATE)
                .description("old description")
                .categories(Set.of(chest))
                .build();

        ExerciseUpdateRequest req = new ExerciseUpdateRequest();
        req.setDescription(JsonNullable.of("new description"));

        Exercise updated = Exercise.builder()
                .id(id)
                .name("Bench Press")
                .difficulty(Difficulty.INTERMEDIATE)
                .description("new description")
                .categories(Set.of(chest))
                .build();

        ExerciseResponse resp = ExerciseResponse.builder()
                .id(id)
                .name("Bench Press")
                .difficulty(Difficulty.INTERMEDIATE)
                .description("new description")
                .categories(List.of(CategoryResponse.builder().id(1L).name("Chest").build()))
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.existsByNameIgnoreCaseAndIdNot("Bench Press", id)).thenReturn(false);
        when(repository.save(any(Exercise.class))).thenReturn(updated);
        when(exerciseMapper.toResponse(updated)).thenReturn(resp);

        ExerciseResponse result = exerciseService.updateExercise(id, req);

        assertEquals(id, result.getId());
        assertEquals("Bench Press", result.getName());
        assertEquals(Difficulty.INTERMEDIATE, result.getDifficulty());
        assertEquals("new description", result.getDescription());
        assertThat(result.getCategories()).extracting("name").containsExactly("Chest");
    }


    @Test
    @DisplayName("Throws NotFound when updating a non-existing id")
    void updateExercise_notFound() {
        Long missingId = 999L;

        ExerciseUpdateRequest req = new ExerciseUpdateRequest();
        req.setDescription(JsonNullable.of("new description"));

        when(repository.findById(missingId)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> exerciseService.updateExercise(missingId, req));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals(THE_EXERCISE_WITH_ID_D_DOES_NOT_EXIST.formatted(missingId), ex.getReason());
    }


    @Test
    @DisplayName("Throws Conflict when updating to a name that belongs to a different id")
    void updateExercise_conflict_excludingSelf() {
        Long id = 1L;

        Category chest = new Category(1L, "Chest");

        Exercise existing = Exercise.builder()
                .id(id)
                .name("Incline Press")
                .difficulty(Difficulty.INTERMEDIATE)
                .description("old")
                .categories(Set.of(chest))
                .build();

        ExerciseUpdateRequest req = new ExerciseUpdateRequest();
        req.setName(JsonNullable.of("Bench Press"));

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.existsByNameIgnoreCaseAndIdNot("Bench Press", id)).thenReturn(true);

        ConflictException ex = assertThrows(ConflictException.class,
                () -> exerciseService.updateExercise(id, req));

        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        assertEquals(ErrorCode.CONFLICT, ex.getCode());
    }


    @Test
    @DisplayName("Updates when only description changes; uniqueness check excludes current id")
    void updateExercise_onlyDescription_ok() {
        Long id = 1L;
        Category chest = new Category(1L, "Chest");

        Exercise existing = Exercise.builder()
                .id(id)
                .name("Incline Press")
                .difficulty(Difficulty.INTERMEDIATE)
                .description("old")
                .categories(Set.of(chest))
                .build();

        ExerciseUpdateRequest req = new ExerciseUpdateRequest();
        req.setDescription(JsonNullable.of("new"));

        Exercise updated = Exercise.builder()
                .id(id)
                .name("Incline Press")
                .difficulty(Difficulty.INTERMEDIATE)
                .description("new")
                .categories(Set.of(chest))
                .build();

        ExerciseResponse resp = ExerciseResponse.builder()
                .id(id)
                .name("Incline Press")
                .difficulty(Difficulty.INTERMEDIATE)
                .description("new")
                .categories(List.of(CategoryResponse.builder().id(1L).name("Chest").build()))
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.existsByNameIgnoreCaseAndIdNot("Incline Press", id)).thenReturn(false);
        when(repository.save(any(Exercise.class))).thenReturn(updated);
        when(exerciseMapper.toResponse(updated)).thenReturn(resp);

        ExerciseResponse result = exerciseService.updateExercise(id, req);

        assertEquals(id, result.getId());
        assertEquals("Incline Press", result.getName());
        assertEquals(Difficulty.INTERMEDIATE, result.getDifficulty());
        assertEquals("new", result.getDescription());
        assertThat(result.getCategories()).extracting("name").containsExactly("Chest");
    }


    @Test
    @DisplayName("Deletes existing exercise without errors")
    void deleteExercise_ok() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> exerciseService.deleteExercise(id));
    }

    @Test
    @DisplayName("Throws NotFound when deleting a non-existing id")
    void deleteExercise_notFound() {
        Long missingId = 999L;
        when(repository.existsById(missingId)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> exerciseService.deleteExercise(missingId));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals(THE_EXERCISE_WITH_ID_D_DOES_NOT_EXIST.formatted(missingId), ex.getReason());
    }


}
