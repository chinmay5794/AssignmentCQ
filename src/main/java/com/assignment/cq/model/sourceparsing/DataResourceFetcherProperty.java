package com.assignment.cq.model.sourceparsing;

import com.assignment.cq.model.*;

import javax.persistence.*;

@Entity
@Table(name = "sp_data_resource_fetcher_property")
public class DataResourceFetcherProperty extends Property {

    @Column(name = "value", nullable = false)
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_type", nullable = false)
    private PropertyType propertyType;

    @ManyToOne
    @JoinColumn(name="data_resource_fetcher", nullable=false, foreignKey=@ForeignKey(name = "fk_resource_fetcher"))
    private DataResourceFetcher dataResourceFetcher;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DataResourceFetcher getDataResourceFetcher() {
        return dataResourceFetcher;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public void setDataResourceFetcher(DataResourceFetcher dataResourceFetcher) {
        this.dataResourceFetcher = dataResourceFetcher;
    }
}
