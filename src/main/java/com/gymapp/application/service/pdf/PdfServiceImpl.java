package com.gymapp.application.service.pdf;

import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.api.dto.programexercise.request.ProgramExerciseRequest;
import com.gymapp.application.pdf.dto.PdfExerciseDto;
import com.gymapp.application.pdf.generator.HtmlToPdfGenerator;
import com.gymapp.application.pdf.util.PdfDataUtils;
import com.gymapp.application.pdf.viewmodel.PdfProgramExerciseViewModel;
import com.gymapp.application.pdf.viewmodel.PdfProgramViewModel;
import com.gymapp.infrastructure.persistence.ExerciseJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gymapp.application.pdf.util.PdfConstants.RUTINA_DE_ENTRENAMIENTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfServiceImpl implements PdfService{


    private final ExerciseJpaRepository exerciseJpaRepository;
    private final HtmlToPdfGenerator pdfGenerator;

    @Override
    public byte[] generateProgramPdf(ProgramRequest request) {
        PdfProgramViewModel pdfProgramViewModel = createPdfProgramViewModel(request);
        return pdfGenerator.generate(pdfProgramViewModel);
    }

    private PdfProgramViewModel createPdfProgramViewModel(ProgramRequest request){
        return PdfProgramViewModel.builder()
                .title(normalizeTitle(request.getTitle()))
                .startDate(getDateText(request.getStartDate()))
                .endDate(getDateText(request.getEndDate()))
                .format(request.getPdfFormatType())
                .dayLabels(Objects.requireNonNullElse(request.getDayLabels(), Map.of()))
                .notes(Objects.requireNonNullElse(request.getNotes(), null))
                .exercisesByDay(getExercisesByDay(request))
                .build();
    }

    private Map<String, List<PdfProgramExerciseViewModel>> getExercisesByDay(ProgramRequest request){
        Map<Long, PdfExerciseDto> exercisesById =
                PdfDataUtils.loadExercisesByIds(request.getProgramExercises(), exerciseJpaRepository);

        Map<String, List<ProgramExerciseRequest>> exercisesByDay = PdfDataUtils.groupExercisesByDay(request);

        return exercisesByDay.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .sorted(Comparator.comparing(ProgramExerciseRequest::getPosition))
                                .map(programExercise -> {
                                    PdfExerciseDto exercise =
                                            exercisesById.get(programExercise.getExerciseId());

                                    return PdfProgramExerciseViewModel.builder()
                                            .name(exercise.getName())
                                            .sets(programExercise.getSets())
                                            .reps(programExercise.getReps())
                                            .restTime(programExercise.getRestTime())
                                            .intensity(programExercise.getIntensity())
                                            .tempo(programExercise.getTempo())
                                            .notes(programExercise.getNotes())
                                            .position(programExercise.getPosition())
                                            .build();
                                })
                                .toList()
                ));
    }

    private String getDateText(LocalDate localDate){
        return localDate != null ? localDate.toString() : "";
    }

    private String normalizeTitle(String title) {
        return (title == null || title.isBlank())
                ? RUTINA_DE_ENTRENAMIENTO
                : title;
    }

}
