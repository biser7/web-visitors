package com.webvisitors.config;

import com.webvisitors.model.Visitor;
import com.webvisitors.repository.VisitorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Writer implements ItemWriter<Visitor> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Writer.class);
    private final VisitorRepository visitorRepository;

    public Writer(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    @Override
    public void write(List<? extends Visitor> visitors) {
        LOGGER.info("Data Saved for Users: {}", visitors);
        visitorRepository.saveAll(visitors);
    }
}
