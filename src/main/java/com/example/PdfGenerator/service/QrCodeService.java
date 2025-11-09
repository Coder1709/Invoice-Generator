package com.example.PdfGenerator.service;

import com.example.PdfGenerator.controller.InvoiceController;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class QrCodeService {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    public byte[] generateQrCode(String text, int width, int height) throws IOException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageConfig config = new MatrixToImageConfig(0xFF000000, 0xFFFFFFFF);
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, config);
            return pngOutputStream.toByteArray();
        } catch (Exception e) {
            logger.error("Failed to generate QR code image.", e);
            throw new IOException("Failed to generate QR code image.", e);
        }
    }
}