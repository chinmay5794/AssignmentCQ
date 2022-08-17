package com.assignment.cq.repositories;

import com.assignment.cq.model.sourceparsing.DataFetcher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataFetcherRepository extends JpaRepository<DataFetcher, Long> {
    List<DataFetcher> findByDataFetcherPath(String dataFetcherPath);
}
