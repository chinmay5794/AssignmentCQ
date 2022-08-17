package com.assignment.cq.model.data;

import com.assignment.cq.model.IdentifiableAuditable;
import com.assignment.cq.model.sourceparsing.DataSource;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "dt_item", uniqueConstraints = @UniqueConstraint(columnNames={"name", "source"}))
@Audited
public class Item extends IdentifiableAuditable {

    @Column(name = "name", nullable = false)
    private String name;

    //@Id
    //@MapsId("source")
    @ManyToOne
    @JoinColumn(name="source", nullable=false, foreignKey=@ForeignKey(name = "fk_item_source"))
    @NotAudited
    private DataSource source;

    @OneToMany(mappedBy = "item")
    private Set<ItemProperty> itemProperties;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataSource getSource() {
        return source;
    }

    public void setSource(DataSource source) {
        this.source = source;
    }

    public Set<ItemProperty> getItemProperties() {
        return itemProperties;
    }

    public void setItemProperties(Set<ItemProperty> itemProperties) {
        this.itemProperties = itemProperties;
    }
}
