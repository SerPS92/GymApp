package com.gymapp.application.service.pdf;

import com.gymapp.api.dto.program.request.ProgramRequest;
import com.gymapp.application.pdf.strategy.base.PdfRenderStrategy;
import com.gymapp.shared.error.AppException;
import com.gymapp.shared.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfServiceImpl implements PdfService{

    private final List<PdfRenderStrategy> strategies;

    @Override
    public byte[] generateProgramPdf(ProgramRequest request) {
        return strategies.stream()
                .filter(s -> s.supports(request.getPdfFormatType()))
                .findFirst()
                .orElseThrow(() -> new AppException(
                        HttpStatus.BAD_REQUEST,
                        ErrorCode.BAD_REQUEST,
                        "Unsupported format type: " + request.getPdfFormatType()
                ))
                .render(request);
    }
}
