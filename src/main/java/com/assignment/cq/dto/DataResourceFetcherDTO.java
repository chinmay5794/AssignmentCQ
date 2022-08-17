package com.assignment.cq.dto;

import com.assignment.cq.model.data.Item;
import com.assignment.cq.model.data.ItemProperty;
import com.assignment.cq.model.sourceparsing.DataResourceFetcher;
import com.assignment.cq.model.sourceparsing.DataResourceFetcherProperty;

import java.util.HashSet;
import java.util.Set;

public class DataResourceFetcherDTO {
    private DataResourceDTO dataResourceDTO;
    private DataFetcherDTO dataFetcherDTO;
    private Set<DataResourceFetcherPropertyDTO> dataResourceFetcherProperties;
    private String cronExpression;
    private String timezone;

    public DataResourceFetcherDTO(DataResourceFetcher dataResourceFetcher, boolean setDataResourceFetcherProperties) {
        setDataResourceDTO(new DataResourceDTO(dataResourceFetcher.getDataResource(), true));
        setDataFetcherDTO(new DataFetcherDTO(dataResourceFetcher.getDataFetcher()));
        if (setDataResourceFetcherProperties) {
            Set<DataResourceFetcherPropertyDTO> dataResourceFetcherPropertyDTOSet = new HashSet<>();
            for (DataResourceFetcherProperty dataResourceFetcherProperty : dataResourceFetcher.getDataResourceFetcherProperties()) {
                DataResourceFetcherPropertyDTO dataResourceFetcherPropertyDTO =
                        new DataResourceFetcherPropertyDTO(dataResourceFetcherProperty, false);
                dataResourceFetcherPropertyDTOSet.add(dataResourceFetcherPropertyDTO);
            }
            setDataResourceFetcherProperties(dataResourceFetcherPropertyDTOSet);
        }
        setCronExpression(dataResourceFetcher.getCronExpression());
        setTimezone(dataResourceFetcher.getTimeZone());
    }

    public DataResourceDTO getDataResourceDTO() {
        return dataResourceDTO;
    }

    public void setDataResourceDTO(DataResourceDTO dataResourceDTO) {
        this.dataResourceDTO = dataResourceDTO;
    }

    public DataFetcherDTO getDataFetcherDTO() {
        return dataFetcherDTO;
    }

    public void setDataFetcherDTO(DataFetcherDTO dataFetcherDTO) {
        this.dataFetcherDTO = dataFetcherDTO;
    }

    public Set<DataResourceFetcherPropertyDTO> getDataResourceFetcherProperties() {
        return dataResourceFetcherProperties;
    }

    public void setDataResourceFetcherProperties(Set<DataResourceFetcherPropertyDTO> dataResourceFetcherProperties) {
        this.dataResourceFetcherProperties = dataResourceFetcherProperties;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
