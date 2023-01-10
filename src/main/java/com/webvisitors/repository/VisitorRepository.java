package com.webvisitors.repository;

import com.webvisitors.model.Visitor;
import com.webvisitors.model.VisitorCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    @Query("""
            SELECT new com.webvisitors.model.VisitorCount(v.email, v.phone, COUNT(v.email)) FROM Visitor AS v
            WHERE v.email  <> '' AND v.phone <> ''
            GROUP BY v.email, v.phone""")
    List<VisitorCount> getVisitorsStatistic();
}
