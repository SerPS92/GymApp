package com.gymapp.application.pdf.util;

import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.api.dto.programexercise.request.ProgramExerciseRequest;
import com.gymapp.application.pdf.dto.PdfExerciseDto;
import com.gymapp.application.pdf.mapper.PdfExerciseMapper;
import com.gymapp.domain.entity.Exercise;
import com.gymapp.infrastructure.persistence.ExerciseJpaRepository;
import com.gymapp.shared.error.ErrorCode;
import com.gymapp.shared.error.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gymapp.shared.error.ErrorConstants.EXERCISES_NOT_FOUND_WITH_IDS;

@Slf4j
public class PdfDataUtils {

    private PdfDataUtils(){}

    public static Map<Long, PdfExerciseDto> loadExercisesByIds(
            List<ProgramExerciseRequest> requests,
            ExerciseJpaRepository exerciseRepository) {

        List<Long> exerciseIds = requests.stream()
                .map(ProgramExerciseRequest::getExerciseId)
                .distinct()
                .toList();

        List<Exercise> exercises = exerciseRepository.findAllById(exerciseIds);

        if (exercises.size() != exerciseIds.size()) {
            List<Long> foundIds = exercises.stream().map(Exercise::getId).toList();
            List<Long> missing = exerciseIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            throw new AppException(
                    HttpStatus.NOT_FOUND,
                    ErrorCode.NOT_FOUND,
                    EXERCISES_NOT_FOUND_WITH_IDS + missing
            );
        }

        log.info("Found {} exercises", exercises.size());

        return exercises.stream()
                .map(PdfExerciseMapper.INSTANCE::toDto)
                .collect(Collectors.toMap(PdfExerciseDto::getId, e -> e));
    }

    public static Map<String, List<ProgramExerciseRequest>> groupExercisesByDay(ProgramRequest request) {
        return request.getProgramExercises().stream()
                .collect(Collectors.groupingBy(ProgramExerciseRequest::getDay));
    }

}
