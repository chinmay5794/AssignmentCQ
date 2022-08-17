package com.assignment.cq.controllers;

import com.assignment.cq.dto.DataFetcherDTO;
import com.assignment.cq.dto.DataResourceDTO;
import com.assignment.cq.model.sourceparsing.DataFetcher;
import com.assignment.cq.model.sourceparsing.DataFetcherType;
import com.assignment.cq.model.sourceparsing.DataResource;
import com.assignment.cq.model.sourceparsing.DataSource;
import com.assignment.cq.repositories.DataFetcherRepository;
import com.assignment.cq.repositories.DataResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/api/v1/data_fetcher")
public class DataFetcherController {
    @Autowired
    private DataFetcherRepository dataFetcherRepository;

    private List<DataFetcherDTO> getDTOListFromEntityList(List<DataFetcher> dataFetcherList) {
        List<DataFetcherDTO> result = new ArrayList<>();
        if (dataFetcherList != null && !dataFetcherList.isEmpty()) {
            for (DataFetcher dataFetcher : dataFetcherList) {
                result.add(new DataFetcherDTO(dataFetcher));
            }
        }
        return result;
    }

    @GetMapping
    public List<DataFetcherDTO> getAll() {
        List<DataFetcher> dataFetcherList = dataFetcherRepository.findAll();
        return getDTOListFromEntityList(dataFetcherList);
    }

    @PostMapping
    public void createDataFetcher(DataFetcherDTO dataFetcherDTO) {
        DataFetcher dataFetcher = new DataFetcher();
        dataFetcher.setDataFetcherPath(dataFetcherDTO.getDataFetcherPath());
        dataFetcher.setType(DataFetcherType.valueOf(dataFetcherDTO.getType()));
        dataFetcherRepository.save(dataFetcher);
    }
}
