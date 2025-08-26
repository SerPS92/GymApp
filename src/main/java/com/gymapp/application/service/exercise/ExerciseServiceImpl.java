package com.gymapp.application.service.exercise;

import com.gymapp.api.dto.exercise.request.ExerciseFilterRequest;
import com.gymapp.api.dto.exercise.response.ExerciseResponse;
import com.gymapp.application.mapper.exercise.ExerciseMapper;
import com.gymapp.domain.entity.Exercise;
import com.gymapp.infrastructure.persistence.ExerciseJpaRepository;
import com.gymapp.shared.dto.PageResponseDTO;
import com.gymapp.shared.error.AppException;
import com.gymapp.shared.error.BadRequestException;
import com.gymapp.shared.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

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
            throw new BadRequestException("Envía exactamente un filtro: name O muscleGroup.");
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
                new AppException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "El ejercicio con el id %d no existe".formatted(id)));

        return exerciseMapper.toResponse(response);
    }
}
