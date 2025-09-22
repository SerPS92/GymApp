package com.gymapp.application.service.exercise;

import com.gymapp.api.dto.exercise.request.ExerciseCreateRequest;
import com.gymapp.api.dto.exercise.request.ExerciseFilterRequest;
import com.gymapp.api.dto.exercise.request.ExerciseUpdateRequest;
import com.gymapp.api.dto.exercise.response.ExerciseResponse;
import com.gymapp.application.mapper.exercise.ExerciseMapper;
import com.gymapp.domain.entity.Exercise;
import com.gymapp.domain.enums.MuscleGroup;
import com.gymapp.infrastructure.persistence.ExerciseJpaRepository;
import com.gymapp.shared.dto.PageResponseDTO;
import com.gymapp.shared.error.AppException;
import com.gymapp.shared.error.BadRequestException;
import com.gymapp.shared.error.ConflictException;
import com.gymapp.shared.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.gymapp.shared.error.ErrorConstants.*;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService{

    private final ExerciseJpaRepository repository;
    private final ExerciseMapper exerciseMapper;

    @Override
    public PageResponseDTO<ExerciseResponse> search(ExerciseFilterRequest filterRequest, Pageable pageable) throws BadRequestException {

        boolean hasName  = StringUtils.hasText(filterRequest.getName());
        boolean hasGroup = filterRequest.getMuscleGroup() != null;

        if (hasName && hasGroup) {
            throw new BadRequestException(SEND_EXACTLY_ONE_FILTER_NAME_OR_MUSCLE_GROUP);
        }

        Page<Exercise> page;
        if (!hasName && !hasGroup) {
            page = repository.findAll(pageable);
        } else if (hasName) {
            String name = filterRequest.getName().trim();
            page = repository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            page = repository.findByMuscleGroup(filterRequest.getMuscleGroup(), pageable);
        }

        List<ExerciseResponse> content = page.map(exerciseMapper::toResponse).getContent();

        return PageResponseDTO.<ExerciseResponse>builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .empty(page.isEmpty())
                .content(content)
                .build();
    }

    @Override
    public ExerciseResponse getById(Long id) {
        Exercise response = repository.findById(id).orElseThrow(()->
                new AppException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, THE_EXERCISE_WITH_ID_D_DOES_NOT_EXIST.formatted(id)));

        return exerciseMapper.toResponse(response);
    }

    @Override
    public ExerciseResponse createExercise(ExerciseCreateRequest request) {
        if(repository.existsByNameIgnoreCaseAndMuscleGroup(request.getName().trim(), request.getMuscleGroup())){
            throw new ConflictException(THE_EXERCISE_ALREADY_EXISTS);
        }
        Exercise exercise = exerciseMapper.toEntity(request);
        Exercise saved = repository.save(exercise);
        return exerciseMapper.toResponse(saved);
    }

    @Override
    public ExerciseResponse updateExercise(Long id, ExerciseUpdateRequest request) {
        //TODO revisar: falta logica para evitar cambiar un ejercicio a otro que ya existe y añadir test
        Exercise existing  = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, THE_EXERCISE_WITH_ID_D_DOES_NOT_EXIST.formatted(id)));

        String targetName = pickText(existing.getName(), request.getName());
        MuscleGroup targetMuscleGroup = pickValue(existing.getMuscleGroup(), request.getMuscleGroup());
        String targetDescription = pickText(existing.getDescription(), request.getDescription());

        boolean unchanged =
                targetName.equals(existing.getName()) &&
                        targetMuscleGroup == existing.getMuscleGroup() &&
                        java.util.Objects.equals(targetDescription, existing.getDescription());

        if (unchanged) {
            return exerciseMapper.toResponse(existing);
        }

        existing.setName(targetName);
        existing.setMuscleGroup(targetMuscleGroup);
        existing.setDescription(targetDescription);

        Exercise saved = repository.save(existing);
        return exerciseMapper.toResponse(saved);
    }

    @Override
    public void deleteExercise(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,THE_EXERCISE_WITH_ID_D_DOES_NOT_EXIST.formatted(id));
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException(CANNOT_DELETE_THE_EXERCISE_IS_REFERENCED, ex);
        }
    }

    private String pickText(String current, JsonNullable<String> incoming) {
        if (incoming != null && incoming.isPresent()) {
            String v = incoming.get();
            if (v != null) {
                String t = v.trim();
                if (!t.isEmpty()) return t;
            }
        }
        return current;
    }

    private <T> T pickValue(T current, JsonNullable<T> incoming) {
        return (incoming != null && incoming.isPresent() && incoming.get() != null) ? incoming.get() : current;
    }

}
