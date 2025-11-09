package com.example.PdfGenerator.repository;


import com.example.PdfGenerator.model.Dealer;

import java.util.Optional;

public interface DealerRepository {
    Optional<Dealer> findById(String dealerId);
}
