package com.quizapp.services.impl;

import com.quizapp.exceptions.DatabaseException;
import com.quizapp.ingestion.Option;
import com.quizapp.repositories.OptionRepository;
import com.quizapp.services.OptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OptionServiceImpl implements OptionService {
    private final OptionRepository optionRepository;

    public OptionServiceImpl(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    @Override
    public void saveOrUpdateOptions(List<Option> options) {
        log.info("Saving or updating {} options", options.size());

        // Assign UUIDs if not already set
        for (Option option : options) {
            if (option.getOptionId() == null || option.getOptionId().isEmpty()) {
                option.setOptionId(UUID.randomUUID().toString());
            }
        }

        try {
            this.optionRepository.insertOptions(options);
            log.info("Options saved or updated successfully");
        } catch (DataAccessException e) {
            log.error("Database error while saving or updating options", e);
            throw new DatabaseException("Database error while saving or updating options: " + e.getMessage());
        }
    }
}

