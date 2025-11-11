package com.gymapp.application.pdf.factory;

import com.gymapp.api.dto.programexercise.request.ProgramExerciseRequest;
import com.gymapp.application.pdf.dto.PdfExerciseDto;
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
            PdfExerciseDto exercise,
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
                exercise.getName(),
                data[0],
                data[1],
                data[2],
                data[3],
                data[4],
                fontSize
        );

        cell.addElement(paragraph);
        return cell;
    }


    public static PdfPCell createTextOnlyCell(
            PdfExerciseDto exercise,
            ProgramExerciseRequest ex,
            float padding,
            int fontSize) {

        PdfPCell cell = new PdfPCell();
        cell.setPadding(padding);

        String[] data = formatExerciseData(ex);
        Paragraph paragraph = buildTextOnlyParagraph(
                exercise.getName(),
                data[0],
                data[1],
                data[2],
                data[3],
                data[4],
                fontSize
        );

        cell.addElement(paragraph);
        return cell;
    }


    private static String[] formatExerciseData(ProgramExerciseRequest ex) {
        String sets = ex.getSets() != null ? ex.getSets() : "-";
        String reps = ex.getReps() != null ? ex.getReps() : "-";
        String rest = ex.getRestTime() != null && !ex.getRestTime().isBlank()
                ? ex.getRestTime().replace("\"", "") + "s"
                : "";
        String notes = (ex.getNotes() != null && !ex.getNotes().isBlank()) ? ex.getNotes() : "";
        String load = (ex.getIntensity() != null && !ex.getIntensity().isBlank()) ? ex.getIntensity() : "";
        return new String[]{sets, reps, rest, load, notes};
    }

    private static Paragraph buildExerciseParagraph(
            String exerciseName, String sets, String reps, String intensity, String rest, String notes, float fontSize) {

        Font nameFont = new Font(Font.HELVETICA, fontSize - 1, Font.BOLD);
        Font dataFont = new Font(Font.HELVETICA, fontSize - 1);
        Font noteFont = new Font(Font.HELVETICA, fontSize - 1, Font.ITALIC, new Color(120, 120, 120));

        Chunk nameChunk = new Chunk(exerciseName, nameFont);

        StringBuilder dataText = new StringBuilder(" " + sets + "x" + reps);
        if (intensity != null && !intensity.isBlank()) dataText.append(" ").append(intensity);
        if (rest != null && !rest.isBlank()) dataText.append(" ").append(rest);

        Chunk dataChunk = new Chunk(dataText.toString(), dataFont);

        Chunk noteChunk = notes.isEmpty()
                ? new Chunk("")
                : new Chunk(" " + notes, noteFont);

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



    private static Paragraph buildTextOnlyParagraph(
            String exerciseName, String sets, String reps, String intensity, String rest, String notes, float fontSize) {

        Font nameFont = new Font(Font.HELVETICA, fontSize, Font.BOLD);
        Font dataFont = new Font(Font.HELVETICA, fontSize - 1);
        Font noteFont = new Font(Font.HELVETICA, fontSize - 1, Font.ITALIC, new Color(120, 120, 120));

        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setLeading(11f);
        paragraph.setSpacingBefore(2f);
        paragraph.setSpacingAfter(2f);

        paragraph.add(new Phrase(exerciseName, nameFont));
        paragraph.add(Chunk.NEWLINE);

        StringBuilder dataText = new StringBuilder(sets + "x" + reps);
        if (intensity != null && !intensity.isBlank()) dataText.append(" ").append(intensity);
        if (rest != null && !rest.isBlank()) dataText.append(" ").append(rest);

        paragraph.add(new Phrase(dataText.toString(), dataFont));
        paragraph.add(Chunk.NEWLINE);

        if (!notes.isEmpty()) paragraph.add(new Phrase(notes, noteFont));

        return paragraph;
    }


}
