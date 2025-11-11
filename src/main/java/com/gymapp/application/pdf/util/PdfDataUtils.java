package com.gymapp.application.pdf.util;

import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.api.dto.programexercise.request.ProgramExerciseRequest;
import com.gymapp.application.pdf.dto.PdfExerciseDto;
import com.gymapp.application.pdf.mapper.PdfExerciseMapper;
import com.gymapp.domain.entity.Exercise;
import com.gymapp.infrastructure.persistence.ExerciseJpaRepository;
import com.gymapp.shared.error.ErrorCode;
import com.gymapp.shared.error.exception.AppException;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public static Map<String, List<ProgramExerciseRequest>> groupExercisesByDay(
            Document document,
            ProgramRequest request) throws DocumentException {

        Map<String, List<ProgramExerciseRequest>> exercisesByDay = request.getProgramExercises().stream()
                .collect(Collectors.groupingBy(ProgramExerciseRequest::getDay));

        List<String> allDays = IntStream.rangeClosed(1, 7)
                .mapToObj(i -> "Day " + i)
                .toList();

        List<String> daysWithExercises = allDays.stream()
                .filter(day -> exercisesByDay.containsKey(day) && !exercisesByDay.get(day).isEmpty())
                .toList();

        if (daysWithExercises.isEmpty()) {
            Paragraph emptyMsg = new Paragraph("No exercises available for this program.",
                    new Font(Font.HELVETICA, 12, Font.ITALIC, Color.GRAY));
            emptyMsg.setAlignment(Element.ALIGN_CENTER);
            document.add(emptyMsg);
            return Map.of();
        }

        return exercisesByDay;
    }
}
