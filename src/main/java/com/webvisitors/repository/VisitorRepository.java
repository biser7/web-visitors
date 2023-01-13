package com.webvisitors.repository;

import com.webvisitors.model.SourceCount;
import com.webvisitors.model.Visitor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    @Query(value = "SELECT source, COUNT(DISTINCT email, phone) AS count FROM visitors WHERE email <> '' AND phone <> '' GROUP BY source",
            nativeQuery = true)
    List<SourceCount> getSourceUniqueVisitors(Pageable pageable);
}
