package com.example.PdfGenerator.repository;

import com.example.PdfGenerator.model.Dealer;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.Optional;

@Repository
public class MockDealerRepository implements DealerRepository {
    private final Map<String, Dealer> dealers = Map.of(
            "D001", new Dealer("D001", "Prestige Auto Sales", "123 Motor Mile", "Greenville", "SC", "29607"),
            "D002", new Dealer("D002", "Cityside Cars", "456 Downtown Ave", "Metropolis", "NY", "10001")
    );

    @Override
    public Optional<Dealer> findById(String dealerId) {
        return Optional.ofNullable(dealers.get(dealerId));
    }
}