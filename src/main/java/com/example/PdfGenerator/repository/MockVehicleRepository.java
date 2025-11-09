package com.example.PdfGenerator.repository;

import com.example.PdfGenerator.model.Vehicle;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Repository
public class MockVehicleRepository implements VehicleRepository {
    private final Map<String, Vehicle> vehicles = Map.of(
            "V100", new Vehicle("V100", "2023", "Toyota", "Camry LE", "1T1V1234567890ABC", new BigDecimal("28500.00")),
            "V101", new Vehicle("V101", "2024", "Honda", "CR-V EX", "2H2V0987654321XYZ", new BigDecimal("32000.00"))
    );

    @Override
    public Optional<Vehicle> findById(String vehicleId) {
        return Optional.ofNullable(vehicles.get(vehicleId));
    }
}