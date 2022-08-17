package com.assignment.cq.dto;

import com.assignment.cq.model.sourceparsing.DataResource;
import com.assignment.cq.model.sourceparsing.DataSource;

import java.util.HashSet;
import java.util.Set;

public class DataSourceDTO {
    private String name;
    private Set<DataResourceDTO> dataResources;
    public DataSourceDTO(DataSource dataSource, boolean setDataResources) {
        setName(dataSource.getName());
        if (setDataResources) {
            Set<DataResourceDTO> dataResourceDTOSet = new HashSet<>();
            for (DataResource dataResource : dataSource.getDataResources()) {
                DataResourceDTO dataResourceDTO = new DataResourceDTO(dataResource, false);
                dataResourceDTOSet.add(dataResourceDTO);
            }
            setDataResources(dataResourceDTOSet);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DataResourceDTO> getDataResources() {
        return dataResources;
    }

    public void setDataResources(Set<DataResourceDTO> dataResources) {
        this.dataResources = dataResources;
    }
}
