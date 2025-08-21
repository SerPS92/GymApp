package com.gymapp.application.service.exercise;

import com.gymapp.api.dto.exercise.request.ExerciseFilterRequest;
import com.gymapp.api.dto.exercise.response.ExerciseResponse;
import com.gymapp.domain.entity.Exercise;
import com.gymapp.infrastructure.persistence.ExerciseJpaRepository;
import com.gymapp.shared.dto.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService{

    private final ExerciseJpaRepository repository;

    @Override
    public PageResponseDTO<ExerciseResponse> search(ExerciseFilterRequest filterRequest, Pageable pageable) throws BadRequestException {

        boolean hasName  = StringUtils.hasText(filterRequest.getName());
        boolean hasGroup = filterRequest.getMuscleGroup() != null;

        if (hasName == hasGroup) {
            throw new BadRequestException("Envía exactamente un filtro: name O muscleGroup.");
        }

        Page<Exercise> page;
        if (hasName) {
            page = repository.findByNameContainingIgnoreCase(filterRequest.getName().trim(), pageable);
        } else {
            page = repository.findByMuscleGroup(filterRequest.getMuscleGroup(), pageable);
        }

        List<ExerciseResponse> content = page.getContent().stream()
                .map(e -> ExerciseResponse.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .muscleGroup(e.getMuscleGroup())
                        .description(e.getDescription())
                        .build())
                .toList();

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
}
