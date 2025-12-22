package com.gymapp.application.pdf.viewmodel;

import com.gymapp.domain.enums.PdfFormatType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PdfProgramViewModel {

    String title;
    LocalDate startDate;
    LocalDate endDate;

    PdfFormatType format;

    Map<String, String> dayLabels;

    List<String> notes;

    Map<String, List<PdfProgramExerciseViewModel>> exercisesByDay;
}
