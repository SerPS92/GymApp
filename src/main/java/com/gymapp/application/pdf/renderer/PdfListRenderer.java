package com.gymapp.application.pdf.renderer;

import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.api.dto.programexercise.request.ProgramExerciseRequest;
import com.gymapp.application.pdf.factory.PdfExerciseBlockFactory;
import com.gymapp.application.pdf.util.PdfDataUtils;
import com.gymapp.domain.entity.Exercise;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class PdfListRenderer {

    public void renderList(Document document,
                           ProgramRequest request,
                           Map<Long, Exercise> exerciseMap) throws DocumentException {

        Map<String, List<ProgramExerciseRequest>> exercisesByDay =
                PdfDataUtils.groupExercisesByDay(document, request);

        if (exercisesByDay.isEmpty()) return;

        Font dayHeaderFont = new Font(Font.HELVETICA, 14, Font.BOLD, Color.DARK_GRAY);
        int exerciseFontSize = 11;

        for (Map.Entry<String, List<ProgramExerciseRequest>> entry : exercisesByDay.entrySet()) {
            String day = entry.getKey();
            List<ProgramExerciseRequest> exercises = entry.getValue();

            PdfPTable dayTable = buildDayTable(day, exercises, exerciseMap, dayHeaderFont, exerciseFontSize);
            document.add(dayTable);

            Paragraph spacer = new Paragraph();
            spacer.setSpacingAfter(10f);
            document.add(spacer);
        }
    }

    private PdfPTable buildDayTable(String day,
                                    List<ProgramExerciseRequest> exercises,
                                    Map<Long, Exercise> exerciseMap,
                                    Font dayHeaderFont,
                                    int exerciseFontSize) throws DocumentException {

        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.setKeepTogether(true);
        table.setSplitLate(false);
        table.setSplitRows(true);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        PdfPCell headerCell = new PdfPCell(new Phrase(day, dayHeaderFont));
        headerCell.setBorder(Rectangle.NO_BORDER);
        headerCell.setPaddingTop(10f);
        headerCell.setPaddingBottom(6f);
        table.addCell(headerCell);

        int exerciseCounter = 0;
        for (ProgramExerciseRequest ex : exercises) {
            Exercise exercise = exerciseMap.get(ex.getExerciseId());
            if (exercise == null) continue;

            Paragraph block = PdfExerciseBlockFactory.createExerciseBlock(exercise, ex, exerciseFontSize);

            PdfPCell cell = new PdfPCell();
            cell.addElement(block);
            cell.setPadding(6f);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(exerciseCounter % 2 == 0
                    ? new Color(247, 247, 247) : Color.WHITE);

            table.addCell(cell);
            exerciseCounter++;
        }

        return table;
    }
}
