package com.gymapp.application.pdf.factory;

import com.gymapp.api.dto.programexercise.request.ProgramExerciseRequest;
import com.gymapp.domain.entity.Exercise;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class PdfExerciseBlockFactory {

    private PdfExerciseBlockFactory() {}

    public static Paragraph createExerciseBlock(Exercise exercise, ProgramExerciseRequest ex, int fontSize) {
        Phrase line = formatExerciseData(exercise, ex, fontSize);
        Paragraph paragraph = new Paragraph(line);
        paragraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.setSpacingBefore(5f);
        paragraph.setSpacingAfter(3f);
        return paragraph;
    }

    private static Phrase formatExerciseData(Exercise exercise, ProgramExerciseRequest ex, int fontSize) {
        Font boldFont = new Font(Font.HELVETICA, fontSize, Font.BOLD, Color.BLACK);
        Font normalFont = new Font(Font.HELVETICA, fontSize, Font.NORMAL, Color.BLACK);

        Phrase phrase = new Phrase();

        phrase.add(new Chunk(exercise.getName(), boldFont));

        String sets = ex.getSets();
        String reps = ex.getReps();
        if (sets != null && !sets.isBlank() && reps != null && !reps.isBlank()) {
            phrase.add(new Chunk(" - " + sets + " series x " + reps + " reps", normalFont));
        }
        if (ex.getRestTime() != null && !ex.getRestTime().isBlank()) {
            phrase.add(new Chunk(" - Descanso " + ex.getRestTime() + "s", normalFont));
        }
        if (ex.getNotes() != null && !ex.getNotes().isBlank()) {
            phrase.add(new Chunk(" - " + ex.getNotes(), normalFont));
        }
        if (ex.getIntensity() != null && !ex.getIntensity().isBlank()) {
            phrase.add(new Chunk(" - " + ex.getIntensity(), normalFont));
        }

        return phrase;
    }
}
