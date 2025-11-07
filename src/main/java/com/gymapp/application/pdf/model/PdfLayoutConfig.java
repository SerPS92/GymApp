package com.gymapp.application.pdf.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PdfLayoutConfig {
    private boolean withImages;
    private float imageSize;
    private float padding;
    private int fontSize;
}
