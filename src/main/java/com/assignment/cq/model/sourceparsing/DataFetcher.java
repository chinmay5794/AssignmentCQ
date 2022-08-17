package com.assignment.cq.model.sourceparsing;

import com.assignment.cq.model.Auditable;
import com.assignment.cq.model.Identifiable;
import com.assignment.cq.model.IdentifiableAuditable;

import javax.persistence.*;

@Entity
@Table(name = "sp_data_fetcher")
public class DataFetcher extends IdentifiableAuditable {

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
}
