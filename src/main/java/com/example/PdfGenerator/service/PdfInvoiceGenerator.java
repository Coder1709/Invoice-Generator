package com.example.PdfGenerator.service;

import com.example.PdfGenerator.model.Invoice;
import com.example.PdfGenerator.repository.InvoiceGenerator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
public class PdfInvoiceGenerator implements InvoiceGenerator {

    private final QrCodeService qrCodeService;
    private final PDType1Font FONT_BOLD = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
    private final PDType1Font FONT_REGULAR = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

    // Define some colors
    private final Color COLOR_PRIMARY = new Color(0, 74, 173); // A nice blue
    private final Color COLOR_TABLE_HEADER = new Color(240, 240, 240); // Light gray

    public PdfInvoiceGenerator(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @Override
    public byte[] generate(Invoice invoice) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4); // Use A4 size
            document.addPage(page);

            try (PDPageContentStream cs = new PDPageContentStream(document, page)) {

                // === 1. ADD LOGO (Placeholder) ===
                // To use this:
                // 1. Add 'logo.png' to your 'src/main/resources' folder.
                // 2. Uncomment the code below.
                /*
                try (InputStream in = getClass().getClassLoader().getResourceAsStream("logo.png")) {
                    if (in != null) {
                        PDImageXObject logo = PDImageXObject.createFromByteArray(document, in.readAllBytes(), "logo.png");
                        cs.drawImage(logo, 50, 750, 100, 50); // Draw at x=50, y=750 with 100x50 size
                    }
                }
                */

                // === 2. INVOICE HEADER ===
                cs.setNonStrokingColor(COLOR_PRIMARY); // Set color to blue
                addText(cs, 350, 780, FONT_BOLD, 22, "INVOICE");
                cs.setNonStrokingColor(Color.BLACK); // Reset to black

                // === 3. DEALER & CUSTOMER INFO ===
                addText(cs, 50, 700, FONT_BOLD, 12, "Sold By:");
                addText(cs, 50, 685, FONT_REGULAR, 10, invoice.getDealer().name());
                addText(cs, 50, 670, FONT_REGULAR, 10, invoice.getDealer().address());
                addText(cs, 50, 655, FONT_REGULAR, 10, invoice.getDealer().city() + ", " + invoice.getDealer().state() + " " + invoice.getDealer().zip());

                addText(cs, 350, 700, FONT_BOLD, 12, "Bill To:");
                addText(cs, 350, 685, FONT_REGULAR, 10, invoice.getCustomerName());

                // === 4. INVOICE METADATA ===
                String timestamp = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(invoice.getInvoiceDate()));
                addText(cs, 350, 655, FONT_BOLD, 10, "Invoice #: " + invoice.getInvoiceNumber());
                addText(cs, 350, 640, FONT_BOLD, 10, "Date: " + timestamp);

                // === 5. VEHICLE DETAILS (in a box) ===
                addText(cs, 50, 580, FONT_BOLD, 14, "Vehicle Details");
                drawRectangle(cs, 45, 520, 520, 50, COLOR_TABLE_HEADER); // Header box

                addText(cs, 50, 550, FONT_BOLD, 10, "Year / Make / Model");
                addText(cs, 300, 550, FONT_BOLD, 10, "VIN");

                drawRectangle(cs, 45, 500, 520, 20, Color.WHITE); // Data box
                addText(cs, 50, 505, FONT_REGULAR, 10, invoice.getVehicle().year() + " " + invoice.getVehicle().make() + " " + invoice.getVehicle().model());
                addText(cs, 300, 505, FONT_REGULAR, 10, invoice.getVehicle().vin());


                // === 6. PRICING DETAILS (in a table) ===
                float tableY = 450;
                float tableX = 300;
                float tableWidth = 265;
                float rowHeight = 25;

                // Draw Header
                drawRectangle(cs, tableX, tableY, tableWidth, rowHeight, COLOR_TABLE_HEADER);
                addText(cs, tableX + 15, tableY + 7, FONT_BOLD, 10, "Summary");
                addText(cs, tableX + 180, tableY + 7, FONT_BOLD, 10, "Amount");
                tableY -= rowHeight;

                // Subtotal Row
                drawRectangle(cs, tableX, tableY, tableWidth, rowHeight, Color.WHITE);
                addText(cs, tableX + 15, tableY + 7, FONT_REGULAR, 10, "Base Price");
                addText(cs, tableX + 180, tableY + 7, FONT_REGULAR, 10, formatCurrency(invoice.getSubtotal()));
                tableY -= rowHeight;

                // Tax Row
                drawRectangle(cs, tableX, tableY, tableWidth, rowHeight, Color.WHITE);
                addText(cs, tableX + 15, tableY + 7, FONT_REGULAR, 10, "Tax (10%)");
                addText(cs, tableX + 180, tableY + 7, FONT_REGULAR, 10, formatCurrency(invoice.getTax()));
                tableY -= rowHeight;

                // Total Row (Bold and with a different background)
                drawRectangle(cs, tableX, tableY, tableWidth, rowHeight, COLOR_PRIMARY);
                addText(cs, tableX + 15, tableY + 7, FONT_BOLD, 12, "Total Price", Color.WHITE);
                addText(cs, tableX + 180, tableY + 7, FONT_BOLD, 12, formatCurrency(invoice.getTotal()), Color.WHITE);

                // === 7. QR CODE ===
                byte[] qrCodeImage = qrCodeService.generateQrCode(invoice.getTransactionId(), 100, 100);
                PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, qrCodeImage, "qr-code.png");
                cs.drawImage(pdImage, 50, 100, 100, 100);
                addText(cs, 50, 90, FONT_REGULAR, 8, "Transaction ID: " + invoice.getTransactionId());
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }

    // --- Helper Methods ---

    private void addText(PDPageContentStream cs, float x, float y, PDType1Font font, float fontSize, String text) throws IOException {
        addText(cs, x, y, font, fontSize, text, Color.BLACK); // Default to black
    }

    private void addText(PDPageContentStream cs, float x, float y, PDType1Font font, float fontSize, String text, Color color) throws IOException {
        cs.beginText();
        cs.setFont(font, fontSize);
        cs.setNonStrokingColor(color);
        cs.newLineAtOffset(x, y);
        cs.showText(text);
        cs.endText();
        cs.setNonStrokingColor(Color.BLACK); // Reset to black
    }

    private String formatCurrency(BigDecimal amount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount);
    }

    private void drawRectangle(PDPageContentStream cs, float x, float y, float width, float height, Color color) throws IOException {
        cs.setNonStrokingColor(color);
        cs.addRect(x, y, width, height);
        cs.fill();
        cs.setNonStrokingColor(Color.BLACK); // Reset to black
    }
}