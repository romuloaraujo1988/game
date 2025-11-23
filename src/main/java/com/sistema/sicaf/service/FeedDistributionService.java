package com.sistema.sicaf.service;

import com.sistema.sicaf.model.FeedDistribution;
import com.sistema.sicaf.repository.FeedDistributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedDistributionService {

    @Autowired
    private FeedDistributionRepository feedDistributionRepository;

    public List<FeedDistribution> findAll() {
        return feedDistributionRepository.findAll();
    }

    public FeedDistribution save(FeedDistribution distribution) {
        return feedDistributionRepository.save(distribution);
    }

    public void deleteById(Long id) {
        feedDistributionRepository.deleteById(id);
    }
}
