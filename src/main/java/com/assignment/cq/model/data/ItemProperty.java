package com.assignment.cq.model.data;

import com.assignment.cq.model.Auditable;
import com.assignment.cq.model.IdentifiableAuditable;
import com.assignment.cq.model.Property;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "dt_item_property")
@Audited
public class ItemProperty extends Property {

    @Column(name = "value")
    private String value;
    //foreignKey=@ForeignKey(name = "fk_property_item")
    @ManyToOne
    @JoinColumn(name="item", nullable=false, foreignKey=@ForeignKey(name = "fk_property_item"))
    /*@JoinColumns(value={
            @JoinColumn(name="item_name", referencedColumnName = "name", nullable=false),
            @JoinColumn(name="item_source", referencedColumnName = "source", nullable=false)},
            foreignKey=@ForeignKey(name = "fk_property_item"))*/
    private Item item;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
