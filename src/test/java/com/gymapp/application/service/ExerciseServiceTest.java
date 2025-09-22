package com.gymapp.application.service;

import com.gymapp.api.dto.exercise.request.ExerciseCreateRequest;
import com.gymapp.api.dto.exercise.request.ExerciseFilterRequest;
import com.gymapp.api.dto.exercise.request.ExerciseUpdateRequest;
import com.gymapp.api.dto.exercise.response.ExerciseResponse;
import com.gymapp.application.mapper.exercise.ExerciseMapper;
import com.gymapp.application.service.exercise.ExerciseServiceImpl;
import com.gymapp.domain.entity.Exercise;
import com.gymapp.domain.enums.MuscleGroup;
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

import static com.gymapp.shared.error.ErrorConstants.THE_EXERCISE_WITH_ID_D_DOES_NOT_EXIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest {


    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    @Mock
    private ExerciseJpaRepository repository;

    @Mock
    private ExerciseMapper exerciseMapper;


    @Test
    @DisplayName("Filters by name and returns mapped page")
    void searchByNameTest(){

        Pageable pageable = PageRequest.of(0, 2);
        ExerciseFilterRequest filter = new ExerciseFilterRequest();
        filter.setName(" press ");

        Exercise exercise1 = new Exercise();
        exercise1.setId(1L);
        exercise1.setName("Bench Press");
        exercise1.setMuscleGroup(MuscleGroup.CHEST);

        Exercise exercise2 = new Exercise();
        exercise2.setId(2L);
        exercise2.setName("Shoulder Press");
        exercise2.setMuscleGroup(MuscleGroup.SHOULDERS);

        Page<Exercise> page = new PageImpl<>(List.of(exercise1, exercise2), pageable, 2);
        when(repository.findByNameContainingIgnoreCase("press", pageable)).thenReturn(page);

        ExerciseResponse response1 = ExerciseResponse.builder().id(1L).name(exercise1.getName()).muscleGroup(exercise1.getMuscleGroup()).build();
        ExerciseResponse response2 = ExerciseResponse.builder().id(2L).name(exercise2.getName()).muscleGroup(exercise2.getMuscleGroup()).build();
        when(exerciseMapper.toResponse(exercise1)).thenReturn(response1);
        when(exerciseMapper.toResponse(exercise2)).thenReturn(response2);

        PageResponseDTO<ExerciseResponse> result = exerciseService.search(filter, pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getPage()).isZero();
        assertThat(result.getSize()).isEqualTo(2);
    }

    @Test
    @DisplayName("Filters by muscleGroup and returns mapped page")
    void searchByMuscleGroupTest(){
        Pageable pageable = PageRequest.of(0, 2);
        ExerciseFilterRequest filter = new ExerciseFilterRequest();
        filter.setMuscleGroup(MuscleGroup.CHEST);

        Exercise exercise1 = new Exercise();
        exercise1.setId(1L);
        exercise1.setName("Bench Press");
        exercise1.setMuscleGroup(MuscleGroup.CHEST);

        Page<Exercise> page = new PageImpl<>(List.of(exercise1), pageable, 1);
        when(repository.findByMuscleGroup(MuscleGroup.CHEST, pageable)).thenReturn(page);
        ExerciseResponse response = ExerciseResponse.builder().id(1L).name(exercise1.getName()).muscleGroup(exercise1.getMuscleGroup()).build();
        when(exerciseMapper.toResponse(exercise1)).thenReturn(response);

        PageResponseDTO<ExerciseResponse> result = exerciseService.search(filter, pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getPage()).isZero();
        assertThat(result.getSize()).isEqualTo(2);

    }

    @Test
    @DisplayName("Without filters uses findAll and maps to DTO")
    void searchNoFiltersUsesFindAllTest() {
        Pageable pageable = PageRequest.of(1, 3);
        ExerciseFilterRequest filter = new ExerciseFilterRequest(); // ambos null

        Exercise e = new Exercise();
        e.setId(4L);
        e.setName("Squat");
        e.setMuscleGroup(MuscleGroup.LEGS);
        Page<Exercise> page = new PageImpl<>(List.of(e), pageable, 4);
        when(repository.findAll(pageable)).thenReturn(page);

        ExerciseResponse r = ExerciseResponse.builder().id(4L).name("Squat").muscleGroup(MuscleGroup.LEGS).build();
        when(exerciseMapper.toResponse(e)).thenReturn(r);

        PageResponseDTO<ExerciseResponse> result = exerciseService.search(filter, pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getTotalElements()).isEqualTo(4);
    }

    @Test
    @DisplayName("Throws BadRequestException when both name and muscleGroup are provided")
    void searchBothFiltersBadRequestTest() {
        Pageable pageable = PageRequest.of(0, 10);
        ExerciseFilterRequest filter = new ExerciseFilterRequest();
        filter.setName("press");
        filter.setMuscleGroup(MuscleGroup.CHEST);

        assertThrows(BadRequestException.class, () -> exerciseService.search(filter, pageable));
    }

    @Test
    @DisplayName("Returns ExerciseResponse for an existing id")
    void getByIdTest(){
        Exercise exercise1 = new Exercise(1L, "testName", MuscleGroup.CHEST, "");
        ExerciseResponse response1 = ExerciseResponse.builder().id(1L).name("testName")
                .muscleGroup(MuscleGroup.CHEST).description("").build();

        when(repository.findById(1L)).thenReturn(Optional.of(exercise1));
        when(exerciseMapper.toResponse(exercise1)).thenReturn(response1);

        ExerciseResponse result = exerciseService.getById(1L);

        assertEquals(1L, result.getId());
        assertEquals("testName", result.getName());
        assertEquals(MuscleGroup.CHEST.name(), result.getMuscleGroup().name());
    }

    @Test
    @DisplayName("Throws NotFound when id does not exist")
    void getByIdThrowException(){
        when(repository.findById(999L)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> exerciseService.getById(999L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(ErrorCode.NOT_FOUND, exception.getCode());
    }

    @Test
    @DisplayName("Creates exercise and returns mapped response")
    void createExerciseTest(){

        ExerciseCreateRequest req = new ExerciseCreateRequest();
        req.setName("Bench Press");
        req.setMuscleGroup(MuscleGroup.CHEST);
        req.setDescription("Classic chest press");

        Exercise toSave = new Exercise();
        toSave.setName("Bench Press");
        toSave.setMuscleGroup(MuscleGroup.CHEST);
        toSave.setDescription("Classic chest press");

        Exercise saved = new Exercise();
        saved.setId(10L);
        saved.setName("Bench Press");
        saved.setMuscleGroup(MuscleGroup.CHEST);
        saved.setDescription("Classic chest press");

        ExerciseResponse resp = ExerciseResponse.builder()
                .id(10L)
                .name("Bench Press")
                .muscleGroup(MuscleGroup.CHEST)
                .description("Classic chest press")
                .build();

        when(repository.existsByNameIgnoreCaseAndMuscleGroup("Bench Press", MuscleGroup.CHEST)).thenReturn(false);
        when(exerciseMapper.toEntity(req)).thenReturn(toSave);
        when(repository.save(toSave)).thenReturn(saved);
        when(exerciseMapper.toResponse(saved)).thenReturn(resp);

        ExerciseResponse result = exerciseService.createExercise(req);

        assertEquals(10L, result.getId());
        assertEquals("Bench Press", result.getName());
        assertEquals(MuscleGroup.CHEST, result.getMuscleGroup());
        assertEquals("Classic chest press", result.getDescription());
    }

    @Test
    @DisplayName("Throws Conflict when exercise already exists (name + muscleGroup)")
    void createExercise_conflict() {
        ExerciseCreateRequest req = new ExerciseCreateRequest();
        req.setName("  Bench Press  ");
        req.setMuscleGroup(MuscleGroup.CHEST);
        req.setDescription("dup");

        when(repository.existsByNameIgnoreCaseAndMuscleGroup("Bench Press", MuscleGroup.CHEST))
                .thenReturn(true);

        ConflictException ex = assertThrows(ConflictException.class,
                () -> exerciseService.createExercise(req));

        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        assertEquals(ErrorCode.CONFLICT, ex.getCode());
    }

    @Test
    @DisplayName("Updates only provided fields and returns mapped response")
    void updateExercise_partial_ok() {
        Long id = 1L;
        Exercise existing = new Exercise();
        existing.setId(id);
        existing.setName("Bench Press");
        existing.setMuscleGroup(MuscleGroup.CHEST);
        existing.setDescription("old description");

        ExerciseUpdateRequest req = new ExerciseUpdateRequest();
        req.setDescription(JsonNullable.of("new description"));

        Exercise updated = new Exercise();
        updated.setId(id);
        updated.setName("Bench Press");
        updated.setMuscleGroup(MuscleGroup.CHEST);
        updated.setDescription("new description");

        ExerciseResponse resp = ExerciseResponse.builder()
                .id(id)
                .name("Bench Press")
                .muscleGroup(MuscleGroup.CHEST)
                .description("new description")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(any(Exercise.class))).thenReturn(updated);
        when(exerciseMapper.toResponse(updated)).thenReturn(resp);

        ExerciseResponse result = exerciseService.updateExercise(id, req);

        assertEquals(id, result.getId());
        assertEquals("Bench Press", result.getName());
        assertEquals(MuscleGroup.CHEST, result.getMuscleGroup());
        assertEquals("new description", result.getDescription());
    }

    @Test
    @DisplayName("Throws NotFound when updating a non-existing id")
    void updateExercise_notFound() {
        long missingId = 999L;

        ExerciseUpdateRequest req = new ExerciseUpdateRequest();
        req.setDescription(JsonNullable.of("new description"));

        when(repository.findById(missingId)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> exerciseService.updateExercise(missingId, req));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals(THE_EXERCISE_WITH_ID_D_DOES_NOT_EXIST.formatted(missingId), ex.getReason());
    }




}
