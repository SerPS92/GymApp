package com.gymapp.application.pdf.util;

import com.gymapp.api.dto.program.request.ProgramRequest;

import java.text.Normalizer;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PdfFileNameGenerator {

    private PdfFileNameGenerator(){}

    public static String generateFileName(ProgramRequest request){
        String title = normalizeText(Objects.requireNonNullElse(request.getTitle(), "Rutina de entrenamiento"));
        String clientName = normalizeText(Objects.requireNonNullElse(request.getClientName(), ""));
        String date = request.getStartDate() != null
                ? request.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                : "";

        return fileNameBuilder(clientName, title, date);
    }

    private static String fileNameBuilder(String clientName, String title, String date) {
        return Stream.of(clientName, title, date)
                .filter(s -> s != null && !s.isBlank())
                .collect(Collectors.joining("_"))
                .concat(".pdf");
    }


    private static String normalizeText(String text) {
        if (text == null) {
            return "";
        }
        String normalized = text.trim().toLowerCase();

        normalized = Normalizer.normalize(normalized, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        normalized = normalized.replaceAll("\\s+", "_");

        normalized = normalized.replaceAll("[^a-z0-9_\\-]", "");

        normalized = normalized.replaceAll("_+", "_");

        return normalized;
    }

}
