package com.gymapp.application.pdf.viewmodel;

import com.gymapp.domain.enums.PdfFormatType;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    String startDate;
    String endDate;

    PdfFormatType format;

    Map<String, String> dayLabels;

    List<String> notes;

    Map<String, List<PdfProgramExerciseViewModel>> exercisesByDay;
}
