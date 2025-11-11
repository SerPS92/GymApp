package com.gymapp.application.pdf.util;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import java.awt.*;
import java.util.List;

public class PdfCalendarUtils {

    private PdfCalendarUtils(){}

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

    public static void finalizePage(Document document, Element content, int pageNumber, int totalPages)
            throws DocumentException {

        if (content != null) {
            document.add(content);
        }

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
