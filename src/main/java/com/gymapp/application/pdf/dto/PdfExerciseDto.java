package com.gymapp.application.pdf.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PdfExerciseDto {
    Long id;
    String name;
    String description;
    String category;
    String imageUrl;
}
