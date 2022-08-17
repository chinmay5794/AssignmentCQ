package com.assignment.cq.repositories;

import com.assignment.cq.model.sourceparsing.DataResourceFetcher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataResourceFetcherRepository extends JpaRepository<DataResourceFetcher, Long> {
}
