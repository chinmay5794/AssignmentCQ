package com.assignment.cq.dto;

import com.assignment.cq.model.sourceparsing.DataFetcher;

public class DataFetcherDTO {
    private String dataFetcherPath;
    private String type;

    public DataFetcherDTO(DataFetcher dataFetcher) {
        setDataFetcherPath(dataFetcher.getDataFetcherPath());
        setType(dataFetcher.getType().name());
    }

    public String getDataFetcherPath() {
        return dataFetcherPath;
    }

    public void setDataFetcherPath(String dataFetcherPath) {
        this.dataFetcherPath = dataFetcherPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
