package com.example.PdfGenerator.repository;


import com.example.PdfGenerator.model.Invoice;

import java.io.IOException;

// A generic interface for generating an invoice from our business model
public interface InvoiceGenerator {
    byte[] generate(Invoice invoice) throws IOException;
}
