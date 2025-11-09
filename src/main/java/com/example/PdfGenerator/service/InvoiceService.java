package com.example.PdfGenerator.service;

import com.example.PdfGenerator.dto.InvoiceRequest;
import com.example.PdfGenerator.model.*;
import com.example.PdfGenerator.repository.DealerRepository;
import com.example.PdfGenerator.repository.InvoiceGenerator;
import com.example.PdfGenerator.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class InvoiceService {

    private final DealerRepository dealerRepository;
    private final VehicleRepository vehicleRepository;
    private final InvoiceGenerator pdfGenerator; // Depends on the INTERFACE

    private static final BigDecimal TAX_RATE = new BigDecimal("0.10");

    // All dependencies are injected as interfaces
    public InvoiceService(DealerRepository dealerRepository,
                          VehicleRepository vehicleRepository,
                          InvoiceGenerator pdfGenerator) {
        this.dealerRepository = dealerRepository;
        this.vehicleRepository = vehicleRepository;
        this.pdfGenerator = pdfGenerator;
    }

    public byte[] createAndGenerateInvoice(InvoiceRequest request) throws IOException {
        // 1. Get Data
        Dealer dealer = dealerRepository.findById(request.dealerId())
                .orElseThrow(() -> new RuntimeException("Dealer not found: " + request.dealerId()));
        Vehicle vehicle = vehicleRepository.findById(request.vehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found: " + request.vehicleId()));

        // 2. Perform Business Logic & build the business model
        Invoice invoice = buildInvoice(request.customerName(), dealer, vehicle);

        // 3. Delegate to the generator
        return pdfGenerator.generate(invoice);
    }

    // This is the core business logic
    private Invoice buildInvoice(String customerName, Dealer dealer, Vehicle vehicle) {
        BigDecimal subtotal = vehicle.basePrice();
        BigDecimal tax = subtotal.multiply(TAX_RATE);
        BigDecimal total = subtotal.add(tax);

        return Invoice.builder()
                .invoiceNumber("INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .invoiceDate(Instant.now())
                .transactionId(UUID.randomUUID().toString())
                .customerName(customerName)
                .dealer(dealer)
                .vehicle(vehicle)
                .subtotal(subtotal)
                .tax(tax)
                .total(total)
                .build();
    }
}