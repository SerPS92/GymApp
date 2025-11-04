package com.gymapp.application.service.pdf.factory;

import com.gymapp.api.dto.programexercise.request.ProgramExerciseRequest;
import com.gymapp.domain.entity.Exercise;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class PdfExerciseCellFactory {

    private PdfExerciseCellFactory() {}

    public static PdfPCell createExerciseCell(
            Exercise exercise,
            ProgramExerciseRequest ex,
            float imageSize,
            float padding,
            int fontSize) {

        PdfPCell cell = new PdfPCell();
        cell.setPadding(padding);

        if (exercise.getImageUrl() != null && !exercise.getImageUrl().isBlank()) {
            try {
                Image img = Image.getInstance("src/main/resources/static" + exercise.getImageUrl());
                img.scaleAbsolute(imageSize, imageSize);
                img.setAlignment(Element.ALIGN_CENTER);
                cell.addElement(img);
            } catch (Exception e) {
                log.warn("Could not load image for exercise {}: {}", exercise.getName(), e.getMessage());
            }
        }

        String[] data = formatExerciseData(ex);
        Paragraph paragraph = buildExerciseParagraph(
                exercise.getName(), data[0], data[1], data[2], data[3], fontSize
        );

        cell.addElement(paragraph);
        return cell;
    }


    private static String[] formatExerciseData(ProgramExerciseRequest ex) {
        String sets = ex.getSets() != null ? ex.getSets() : "-";
        String reps = ex.getReps() != null ? ex.getReps() : "-";
        String rest = ex.getRestTime() != null && !ex.getRestTime().isBlank()
                ? ex.getRestTime().replace("\"", "") + "\""
                : "";
        String notes = (ex.getNotes() != null && !ex.getNotes().isBlank()) ? ex.getNotes() : "";
        return new String[]{sets, reps, rest, notes};
    }

    private static Paragraph buildExerciseParagraph(
            String exerciseName, String sets, String reps, String rest, String notes, float fontSize) {

        Font nameFont = new Font(Font.HELVETICA, fontSize - 1, Font.BOLD);
        Font dataFont = new Font(Font.HELVETICA, fontSize - 1);
        Font noteFont = new Font(Font.HELVETICA, fontSize - 1, Font.ITALIC, new Color(120, 120, 120));

        Chunk nameChunk = new Chunk(exerciseName, nameFont);
        Chunk dataChunk = new Chunk(" " + sets + "x" + reps + " " + rest, dataFont);
        Chunk noteChunk = notes.isEmpty() ? new Chunk("") : new Chunk(" " + notes, noteFont);

        Phrase phrase = new Phrase();
        phrase.add(nameChunk);
        phrase.add(dataChunk);
        phrase.add(noteChunk);

        Paragraph paragraph = new Paragraph(phrase);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setLeading(11f);
        paragraph.setSpacingBefore(2f);

        return paragraph;
    }


}
