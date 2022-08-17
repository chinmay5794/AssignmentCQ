package com.assignment.cq.controllers;

import com.assignment.cq.dto.DataSourceDTO;
import com.assignment.cq.model.sourceparsing.DataSource;
import com.assignment.cq.repositories.DataSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/api/v1/data_source")
public class DataSourceController {

    @Autowired
    private DataSourceRepository dataSourceRepository;

    private List<DataSourceDTO> getDTOListFromEntityList(List<DataSource> dataSourcesList) {
        List<DataSourceDTO> result = new ArrayList<>();
        if (dataSourcesList != null && !dataSourcesList.isEmpty()) {
            for (DataSource dataSource : dataSourcesList) {
                result.add(new DataSourceDTO(dataSource, true));
            }
        }
        return result;
    }

    @GetMapping
    public List<DataSourceDTO> getAll() {
        List<DataSource> dataSourcesList = dataSourceRepository.findAll();
        return getDTOListFromEntityList(dataSourcesList);
    }

    @GetMapping("/{name}")
    public List<DataSourceDTO> getByName(@PathVariable String name) {
        List<DataSource> dataSourcesList = dataSourceRepository.findByName(name);
        return getDTOListFromEntityList(dataSourcesList);
    }

    @PostMapping("/{name}")
    public void createDataSource(@PathVariable String name) {
        DataSource dataSource = new DataSource();
        dataSource.setName(name);
        dataSourceRepository.save(dataSource);
    }

    @DeleteMapping("/{name}")
    public void deleteDataSource(@PathVariable String name) {
        List<DataSource> dataSourcesList = dataSourceRepository.findByName(name);
        if (dataSourcesList == null || dataSourcesList.isEmpty()) {
            throw new IllegalArgumentException("No data source with name " + name);
        } else {
            dataSourceRepository.delete(dataSourcesList.get(0));
        }
    }
}
