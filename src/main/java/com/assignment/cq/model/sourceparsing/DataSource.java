package com.assignment.cq.model.sourceparsing;

import com.assignment.cq.model.Auditable;
import com.assignment.cq.model.IdentifiableAuditable;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sp_data_source")
public class DataSource extends IdentifiableAuditable {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "dataSource")
    private Set<DataResource> dataResources;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DataResource> getDataResources() {
        return dataResources;
    }

    public void setDataResources(Set<DataResource> dataResources) {
        this.dataResources = dataResources;
    }
}
