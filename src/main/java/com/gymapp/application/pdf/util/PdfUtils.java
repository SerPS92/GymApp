package com.gymapp.application.pdf.util;

import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.domain.enums.PdfFormatType;
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

    public static Document createDocument(ByteArrayOutputStream out, PdfFormatType type) throws DocumentException {
        Document document;

        if (type == PdfFormatType.LIST) {
            document = new Document(PageSize.A4, 36, 36, 36, 36);
        } else {
            document = new Document(PageSize.A4.rotate(), 36, 36, 10, 0);
        }

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

        String dateText = String.format("%s - %s", request.getStartDate(), request.getEndDate());

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

    public static void addListHeader(Document document, ProgramRequest request) throws DocumentException {
        Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font dateFont = new Font(Font.HELVETICA, 11);

        String titleText = (request.getTitle() != null && !request.getTitle().isBlank())
                ? request.getTitle()
                : "Training Program";

        String dateText = String.format("%s - %s", request.getStartDate(), request.getEndDate());

        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{3f, 2f});

        PdfPCell leftCell = new PdfPCell();
        leftCell.setBorder(Rectangle.NO_BORDER);
        leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        leftCell.setPaddingTop(25f);
        leftCell.setPaddingBottom(2f);

        Paragraph titlePara = new Paragraph(titleText, titleFont);
        titlePara.setSpacingAfter(2f);

        Paragraph datePara = new Paragraph(dateText, dateFont);
        datePara.setSpacingAfter(0f);

        leftCell.addElement(titlePara);
        leftCell.addElement(datePara);


        PdfPCell rightCell = new PdfPCell(new Phrase(""));
        rightCell.setBorder(Rectangle.NO_BORDER);
        rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        rightCell.setPaddingTop(20f);

        headerTable.addCell(leftCell);
        headerTable.addCell(rightCell);

        document.add(headerTable);

        Paragraph spacer = new Paragraph(" ");
        spacer.setSpacingAfter(6f);
        document.add(spacer);
    }

    public static void addProgramNotes(Document document, List<String> notes) throws DocumentException {
        if (notes == null || notes.isEmpty()) return;

        Font notesTitleFont = new Font(Font.HELVETICA, 13, Font.BOLD, Color.DARK_GRAY);
        Font notesTextFont = new Font(Font.HELVETICA, 11, Font.NORMAL, Color.BLACK);

        PdfPTable notesTable = new PdfPTable(1);
        notesTable.setWidthPercentage(100);
        notesTable.setKeepTogether(true);
        notesTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        PdfPCell titleCell = new PdfPCell(new Phrase("Notes:", notesTitleFont));
        titleCell.setBorder(Rectangle.NO_BORDER);
        titleCell.setPaddingTop(20f);
        titleCell.setPaddingBottom(6f);
        notesTable.addCell(titleCell);

        for (String note : notes) {
            if (note != null && !note.isBlank()) {
                Paragraph noteLine = new Paragraph(note.trim(), notesTextFont);
                PdfPCell noteCell = new PdfPCell();
                noteCell.addElement(noteLine);
                noteCell.setBorder(Rectangle.NO_BORDER);
                noteCell.setPaddingBottom(2f);
                notesTable.addCell(noteCell);
            }
        }

        document.add(notesTable);
    }






}
