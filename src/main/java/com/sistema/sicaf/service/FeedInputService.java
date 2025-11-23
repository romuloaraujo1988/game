package com.sistema.sicaf.service;

import com.sistema.sicaf.model.FeedInput;
import com.sistema.sicaf.repository.FeedInputRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedInputService {

    @Autowired
    private FeedInputRepository feedInputRepository;

    public List<FeedInput> findAll() {
        return feedInputRepository.findAll();
    }

    public FeedInput save(FeedInput input) {
        return feedInputRepository.save(input);
    }

    public Optional<FeedInput> findById(Long id) {
        return feedInputRepository.findById(id);
    }

    public void deleteById(Long id) {
        feedInputRepository.deleteById(id);
    }
}
