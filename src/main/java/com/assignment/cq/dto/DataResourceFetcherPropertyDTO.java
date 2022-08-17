package com.assignment.cq.dto;

import com.assignment.cq.model.PropertyType;
import com.assignment.cq.model.data.ItemProperty;
import com.assignment.cq.model.sourceparsing.DataResourceFetcher;
import com.assignment.cq.model.sourceparsing.DataResourceFetcherProperty;

public class DataResourceFetcherPropertyDTO {

    private String name;
    private String value;
    private String propertyType;
    private DataResourceFetcherDTO dataResourceFetcherDTO;

    public DataResourceFetcherPropertyDTO(DataResourceFetcherProperty property, boolean setDataResourceFetcher) {
        if (setDataResourceFetcher) {
            setDataResourceFetcherDTO(new DataResourceFetcherDTO(
                    property.getDataResourceFetcher(), false));
        }
        setName(property.getName());
        setValue(property.getValue());
        setPropertyType(property.getPropertyType().name());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public DataResourceFetcherDTO getDataResourceFetcherDTO() {
        return dataResourceFetcherDTO;
    }

    public void setDataResourceFetcherDTO(DataResourceFetcherDTO dataResourceFetcherDTO) {
        this.dataResourceFetcherDTO = dataResourceFetcherDTO;
    }
}
