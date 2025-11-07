package com.gymapp.application.pdf.util;

import com.gymapp.api.dto.program.request.ProgramRequest;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Slf4j
public class PdfUtils {

    private PdfUtils() {}

    public static Document createDocument(ByteArrayOutputStream out) throws DocumentException {
        Document document = new Document(PageSize.A4.rotate(), 36, 36, 10, 0);
        PdfWriter.getInstance(document, out);
        document.open();
        return document;
    }

    public static void addHeader(Document document, ProgramRequest request) throws DocumentException {
        Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font dateFont = new Font(Font.HELVETICA, 11);

        String titleText = (request.getTitle() != null && !request.getTitle().isBlank())
                ? request.getTitle()
                : "Training Program";

        String dateText = String.format("From %s to %s", request.getStartDate(), request.getEndDate());

        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{3f, 2f});

        PdfPCell titleCell = new PdfPCell(new Phrase(titleText, titleFont));
        titleCell.setBorder(Rectangle.NO_BORDER);
        titleCell.setHorizontalAlignment(Element.ALIGN_LEFT);

        PdfPCell dateCell = new PdfPCell(new Phrase(dateText, dateFont));
        dateCell.setBorder(Rectangle.NO_BORDER);
        dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        headerTable.addCell(titleCell);
        headerTable.addCell(dateCell);

        document.add(headerTable);

        Paragraph spacer = new Paragraph(" ");
        spacer.setSpacingAfter(6f);
        document.add(spacer);
    }

    public static void addDayHeaders(PdfPTable table, List<String> daysOfWeek) {
        Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD);
        for (String day : daysOfWeek) {
            PdfPCell header = new PdfPCell(new Phrase(day, headerFont));
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setBackgroundColor(Color.LIGHT_GRAY);
            header.setPadding(6f);
            table.addCell(header);
        }
    }

    public static void addPageIndicator(Document document, int pageNumber, int totalPages) throws DocumentException {
        Paragraph pageIndicator = new Paragraph(
                String.format("Page %d / %d", pageNumber, totalPages),
                new Font(Font.HELVETICA, 9, Font.ITALIC, Color.GRAY)
        );
        pageIndicator.setAlignment(Element.ALIGN_RIGHT);
        pageIndicator.setSpacingBefore(6f);
        document.add(pageIndicator);
    }

    public static void finalizePage(
            Document document,
            PdfPTable table,
            int pageNumber,
            int totalPages) throws DocumentException {

        document.add(table);
        addPageIndicator(document, pageNumber, totalPages);
        if (pageNumber < totalPages) {
            document.newPage();
        }
    }

    public static float[] resolveLayoutConfig(int maxRows) {
        if (maxRows <= 5) {
            return new float[]{70f, 6f, 10};
        } else {
            return new float[]{60f, 5f, 9};
        }
    }

    public static float[] resolveLayoutConfigNoImage(int maxRows) {
        if (maxRows <= 6) return new float[]{6f, 10};
        if (maxRows <= 8) return new float[]{5f, 9};
        if (maxRows <= 10) return new float[]{4.5f, 8};
        return new float[]{3.5f, 7};
    }

}
