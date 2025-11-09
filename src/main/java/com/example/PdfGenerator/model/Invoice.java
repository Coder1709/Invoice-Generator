package com.example.PdfGenerator.model;



import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
public class Invoice {
    // Metadata
    private final String invoiceNumber;
    private final Instant invoiceDate;
    private final String transactionId;

    // Participants
    private final String customerName;
    private final Dealer dealer;
    private final Vehicle vehicle;

    // Financials
    private final BigDecimal subtotal;
    private final BigDecimal tax;
    private final BigDecimal total;
}
