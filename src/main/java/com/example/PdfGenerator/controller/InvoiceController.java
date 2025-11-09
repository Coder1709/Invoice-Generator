package com.example.PdfGenerator.controller;

import com.example.PdfGenerator.dto.InvoiceRequest;
import com.example.PdfGenerator.service.InvoiceService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/generate")
    public ResponseEntity<Resource> generateInvoice(@RequestBody InvoiceRequest request) {
        try {
            byte[] pdfBytes = invoiceService.createAndGenerateInvoice(request);

            ByteArrayResource resource = new ByteArrayResource(pdfBytes);
            String filename = "invoice_" + System.currentTimeMillis() + ".pdf";

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(pdfBytes.length)
                    .body(resource);

        } catch (IOException e) {
            logger.error("Failed to generate PDF invoice.", e);
            return ResponseEntity.internalServerError().build();

        } catch (RuntimeException e) {
            logger.error("Failed to find data for request: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}