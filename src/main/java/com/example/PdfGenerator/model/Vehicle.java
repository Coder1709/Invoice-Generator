package com.example.PdfGenerator.model;



import java.math.BigDecimal;

public record Vehicle(String id, String year, String make, String model, String vin, BigDecimal basePrice) {}