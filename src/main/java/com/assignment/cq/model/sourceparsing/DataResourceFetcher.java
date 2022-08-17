package com.assignment.cq.model.sourceparsing;

import com.assignment.cq.model.Auditable;
import com.assignment.cq.model.Identifiable;
import com.assignment.cq.model.IdentifiableAuditable;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sp_data_resource_fetcher", uniqueConstraints = {@UniqueConstraint(columnNames = {"data_resource", "data_fetcher"})})
public class DataResourceFetcher extends IdentifiableAuditable {

    @OneToOne
    @JoinColumn(name = "data_resource", nullable=false, foreignKey=@ForeignKey(name = "fk_data_resource"))
    private DataResource dataResource;

    @OneToOne
    @JoinColumn(name = "data_fetcher", nullable=false, foreignKey=@ForeignKey(name = "fk_data_fetcher"))
    private DataFetcher dataFetcher;

    @OneToMany(mappedBy = "dataResourceFetcher")
    private Set<DataResourceFetcherProperty> dataResourceFetcherProperties;

    @Column(name = "cron_expression")
    private String cronExpression;

    @Column(name = "timezone")
    private String timeZone;

    public DataResource getDataResource() {
        return dataResource;
    }

    public void setDataResource(DataResource dataResource) {
        this.dataResource = dataResource;
    }

    public DataFetcher getDataFetcher() {
        return dataFetcher;
    }

    public void setDataFetcher(DataFetcher dataFetcher) {
        this.dataFetcher = dataFetcher;
    }

    public Set<DataResourceFetcherProperty> getDataResourceFetcherProperties() {
        return dataResourceFetcherProperties;
    }

    public void setDataResourceFetcherProperties(Set<DataResourceFetcherProperty> dataResourceFetcherProperties) {
        this.dataResourceFetcherProperties = dataResourceFetcherProperties;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
