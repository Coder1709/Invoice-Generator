package com.example.PdfGenerator.repository;



import com.example.PdfGenerator.model.Vehicle;

import java.util.Optional;

public interface VehicleRepository {
    Optional<Vehicle> findById(String vehicleId);
}
