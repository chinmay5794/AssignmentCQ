package com.assignment.cq.controllers;

import com.assignment.cq.dto.DataResourceDTO;
import com.assignment.cq.model.sourceparsing.DataResource;
import com.assignment.cq.repositories.DataResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/api/v1/data_resource")
public class DataResourceController {
    @Autowired
    private DataResourceRepository dataResourceRepository;

    private List<DataResourceDTO> getDTOListFromEntityList(List<DataResource> dataResourcesList) {
        List<DataResourceDTO> result = new ArrayList<>();
        if (dataResourcesList != null && !dataResourcesList.isEmpty()) {
            for (DataResource dataResource : dataResourcesList) {
                result.add(new DataResourceDTO(dataResource, true));
            }
        }
        return result;
    }

    @GetMapping
    public List<DataResourceDTO> getAll() {
        List<DataResource> dataResourcesList = dataResourceRepository.findAll();
        return getDTOListFromEntityList(dataResourcesList);
    }

    @GetMapping("/{dataSourceName}")
    public List<DataResourceDTO> getByName(@PathVariable String dataSourceName) {
        List<DataResource> dataSourcesList = dataResourceRepository.findByDataSource_Name(dataSourceName);
        return getDTOListFromEntityList(dataSourcesList);
    }
}
