package com.assignment.cq.model.sourceparsing;

import com.assignment.cq.model.Auditable;
import com.assignment.cq.model.Identifiable;
import com.assignment.cq.model.IdentifiableAuditable;
import com.google.gson.JsonArray;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "sp_data_fetcher")
public abstract class DataFetcher extends IdentifiableAuditable {

    @Column(name = "data_fetcher_path", nullable = false)
    private String dataFetcherPath;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private DataFetcherType type;

    public String getDataFetcherPath() {
        return dataFetcherPath;
    }

    public void setDataFetcherPath(String dataFetcherPath) {
        this.dataFetcherPath = dataFetcherPath;
    }

    public DataFetcherType getType() {
        return type;
    }

    public void setType(DataFetcherType type) {
        this.type = type;
    }

    public abstract void validateProperties(Map<String, String> properties);
    public abstract JsonArray fetchData(String resource, Map<String, String> properties) throws Exception;
}
