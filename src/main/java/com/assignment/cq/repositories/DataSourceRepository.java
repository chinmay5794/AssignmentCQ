package com.assignment.cq.repositories;

import com.assignment.cq.model.data.ItemProperty;
import com.assignment.cq.model.sourceparsing.DataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataSourceRepository extends JpaRepository<DataSource, Long> {
    List<DataSource> findByName(String name);
}
