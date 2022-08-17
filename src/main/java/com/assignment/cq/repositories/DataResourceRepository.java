package com.assignment.cq.repositories;

import com.assignment.cq.model.sourceparsing.DataResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataResourceRepository extends JpaRepository<DataResource, Long> {
    List<DataResource> findByDataSource_Name(String dataSourceName);
}
