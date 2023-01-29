package com.webvisitors.config;

import com.webvisitors.model.Visitor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ItemProcessor<Visitor, Visitor> {
    /**
     * Set emails to lowercase
     * @param visitor object from CS line
     * @return visitor object with updated field
     */
    @Override
    public Visitor process(Visitor visitor) {
        visitor.setEmail(visitor.getEmail().toLowerCase());
        return visitor;
    }
}
