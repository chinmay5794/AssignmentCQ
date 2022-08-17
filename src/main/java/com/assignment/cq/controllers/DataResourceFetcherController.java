package com.assignment.cq.controllers;

import com.assignment.cq.dto.DataFetcherDTO;
import com.assignment.cq.dto.DataResourceFetcherDTO;
import com.assignment.cq.model.sourceparsing.*;
import com.assignment.cq.repositories.DataFetcherRepository;
import com.assignment.cq.repositories.DataResourceFetcherRepository;
import com.assignment.cq.repositories.DataResourceRepository;
import com.assignment.cq.repositories.DataSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/api/v1/data_resource_fetcher")
public class DataResourceFetcherController {
    @Autowired
    private DataResourceFetcherRepository dataResourceFetcherRepository;

    @Autowired
    private DataFetcherRepository dataFetcherRepository;

    @Autowired
    private DataResourceRepository dataResourceRepository;

    @Autowired
    private DataSourceRepository dataSourceRepository;

    private List<DataResourceFetcherDTO> getDTOListFromEntityList(List<DataResourceFetcher> dataResourceFetcherList) {
        List<DataResourceFetcherDTO> result = new ArrayList<>();
        if (dataResourceFetcherList != null && !dataResourceFetcherList.isEmpty()) {
            for (DataResourceFetcher dataResourceFetcher : dataResourceFetcherList) {
                result.add(new DataResourceFetcherDTO(dataResourceFetcher, true));
            }
        }
        return result;
    }

    @GetMapping
    public List<DataResourceFetcherDTO> getAll() {
        List<DataResourceFetcher> dataResourceFetcherList = dataResourceFetcherRepository.findAll();
        return getDTOListFromEntityList(dataResourceFetcherList);
    }

    @PostMapping
    public void createDataResourceFetcher(DataResourceFetcherDTO dataResourceFetcherDTO) {
        DataResourceFetcher dataResourceFetcher = new DataResourceFetcher();

        // get data fetcher
        String dataFetcherPath = dataResourceFetcherDTO.getDataFetcherDTO().getDataFetcherPath();
        List<DataFetcher> dataFetcherList = dataFetcherRepository.findByDataFetcherPath(dataFetcherPath);
        if (dataFetcherList == null || dataFetcherList.isEmpty()) {
            throw new IllegalArgumentException("No data fetcher with path " + dataFetcherPath);
        }
        dataResourceFetcher.setDataFetcher(dataFetcherList.get(0));

        // get data source
        String dataSourceName = dataResourceFetcherDTO.getDataResourceDTO().getDataSourceDTO().getName();
        List<DataSource> dataSourceList = dataSourceRepository.findByName(dataSourceName);
        if (dataSourceList == null || dataSourceList.isEmpty()) {
            throw new IllegalArgumentException("No data source with name " + dataSourceName);
        }
        // prepare data resource
        DataResource dataResource = new DataResource();
        dataResource.setResource(dataResourceFetcherDTO.getDataResourceDTO().getResource());
        dataResource.setDataSource(dataSourceList.get(0));
        dataResource = dataResourceRepository.save(dataResource);
        dataResourceFetcher.setDataResource(dataResource);

        dataResourceFetcherRepository.save(dataResourceFetcher);
    }
}
