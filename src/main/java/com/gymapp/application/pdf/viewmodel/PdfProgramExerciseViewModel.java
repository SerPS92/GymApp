package com.gymapp.application.pdf.viewmodel;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PdfProgramExerciseViewModel {

    String name;

    String sets;
    String reps;
    String restTime;
    String intensity;
    String tempo;
    String notes;

    Integer position;
}
