package com.assignment.cq.model.sourceparsing;

import com.assignment.cq.model.Auditable;
import com.assignment.cq.model.IdentifiableAuditable;

import javax.persistence.*;

@Entity
@Table(name = "sp_data_resource")
public class DataResource extends IdentifiableAuditable {

    @Column(name = "resource", nullable = false)
    private String resource;

    @ManyToOne
    @JoinColumn(name="data_source", nullable=false, foreignKey=@ForeignKey(name = "fk_resource_source"))
    private DataSource dataSource;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
