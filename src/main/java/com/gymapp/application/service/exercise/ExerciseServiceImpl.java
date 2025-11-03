package com.gymapp.application.service.exercise;

import com.gymapp.api.dto.exercise.request.ExerciseCreateRequest;
import com.gymapp.api.dto.exercise.request.ExerciseFilterRequest;
import com.gymapp.api.dto.exercise.request.ExerciseUpdateRequest;
import com.gymapp.api.dto.exercise.response.ExerciseResponse;
import com.gymapp.application.mapper.exercise.ExerciseMapper;
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
import lombok.RequiredArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.gymapp.shared.error.ErrorConstants.*;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService{


    private final ExerciseJpaRepository exerciseRepository;
    private final CategoryJpaRepository categoryRepository;
    private final ExerciseMapper exerciseMapper;

    @Override
    public PageResponseDTO<ExerciseResponse> search(ExerciseFilterRequest filterRequest, Pageable pageable) throws BadRequestException {

        boolean hasName = StringUtils.hasText(filterRequest.getName());
        boolean hasCategories = filterRequest.getCategoryIds() != null && !filterRequest.getCategoryIds().isEmpty();
        boolean hasDifficulty = filterRequest.getDifficulty() != null;

        if (hasName && (hasCategories || hasDifficulty)) {
            throw new BadRequestException(CANNOT_COMBINE_NAME_WITH_OTHER_FILTERS);
        }

        Page<Exercise> page;

        if (hasName) {
            page = exerciseRepository.findByNameContainingIgnoreCase(filterRequest.getName().trim(), pageable);
        }
        else if (hasCategories && hasDifficulty) {
            page = exerciseRepository.findByCategoryIdsAndDifficulty(filterRequest.getCategoryIds(), filterRequest.getDifficulty(), pageable);
        }
        else if (hasCategories) {
            page = exerciseRepository.findByCategoryIds(filterRequest.getCategoryIds(), pageable);
        }
        else if (hasDifficulty) {
            page = exerciseRepository.findByDifficulty(filterRequest.getDifficulty(), pageable);
        }
        else {
            page = exerciseRepository.findAll(pageable);
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
        Exercise response = exerciseRepository.findById(id).orElseThrow(()->
                new AppException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, THE_EXERCISE_WITH_ID_D_DOES_NOT_EXIST.formatted(id)));

        return exerciseMapper.toResponse(response);
    }

    @Override
    public ExerciseResponse createExercise(ExerciseCreateRequest request) {
        String name = request.getName().trim();

        if (exerciseRepository.existsByNameIgnoreCase(name)) {
            throw new ConflictException(THE_EXERCISE_ALREADY_EXISTS);
        }

        List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());
        if (categories.isEmpty()) {
            throw new BadRequestException("At least one valid categoryId must be provided");
        }

        Exercise exercise = exerciseMapper.toEntity(request);
        exercise.setCategories(new HashSet<>(categories));

        //Imagen MVP -> Siguiente versión incluye la lógica real de subida
        exercise.setImageUrl(request.getImageUrl() != null ? request.getImageUrl() : "/images/default.jpg");
        exercise.setImageName(null);
        exercise.setImagePath(null);

        Exercise saved = exerciseRepository.save(exercise);

        return exerciseMapper.toResponse(saved);
    }

    @Override
    public ExerciseResponse updateExercise(Long id, ExerciseUpdateRequest request) {

        Exercise existing = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        THE_EXERCISE_WITH_ID_D_DOES_NOT_EXIST.formatted(id)
                ));

        String targetName = pickText(existing.getName(), request.getName());
        Difficulty targetDifficulty = pickValue(existing.getDifficulty(), request.getDifficulty());
        String targetDescription = pickText(existing.getDescription(), request.getDescription());

        Set<Category> targetCategories = existing.getCategories();

        if (request.getCategoryIds().isPresent()) {
            List<Category> categories = categoryRepository.findAllById(request.getCategoryIds().get());
            if (categories.isEmpty()) {
                throw new BadRequestException("At least one valid categoryId must be provided");
            }
            targetCategories = new HashSet<>(categories);
        }

        boolean unchanged =
                targetName.equals(existing.getName()) &&
                        targetDifficulty == existing.getDifficulty() &&
                        java.util.Objects.equals(targetDescription, existing.getDescription()) &&
                        targetCategories.equals(existing.getCategories());

        if (unchanged) {
            return exerciseMapper.toResponse(existing);
        }

        if (exerciseRepository.existsByNameIgnoreCaseAndIdNot(targetName, id)) {
            throw new ConflictException(THE_EXERCISE_ALREADY_EXISTS);
        }

        existing.setName(targetName);
        existing.setDifficulty(targetDifficulty);
        existing.setDescription(targetDescription);
        existing.setCategories(targetCategories);

        Exercise saved = exerciseRepository.save(existing);
        return exerciseMapper.toResponse(saved);
    }

    @Override
    public void deleteExercise(Long id) {
        if (!exerciseRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,THE_EXERCISE_WITH_ID_D_DOES_NOT_EXIST.formatted(id));
        }
        try {
            exerciseRepository.deleteById(id);
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
