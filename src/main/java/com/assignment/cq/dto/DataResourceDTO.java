package com.assignment.cq.dto;

import com.assignment.cq.model.sourceparsing.DataResource;

public class DataResourceDTO {
    private String resource;
    private DataSourceDTO dataSourceDTO;

    public DataResourceDTO(DataResource dataResource, boolean setDataSource) {
        if (setDataSource) {
            setDataSourceDTO(new DataSourceDTO(dataResource.getDataSource(), false));
        }
        setResource(dataResource.getResource());
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public DataSourceDTO getDataSourceDTO() {
        return dataSourceDTO;
    }

    public void setDataSourceDTO(DataSourceDTO dataSourceDTO) {
        this.dataSourceDTO = dataSourceDTO;
    }
}
