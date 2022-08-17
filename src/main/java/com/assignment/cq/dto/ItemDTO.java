package com.assignment.cq.dto;

import com.assignment.cq.model.data.Item;
import com.assignment.cq.model.data.ItemProperty;

import java.util.HashSet;
import java.util.Set;

public class ItemDTO {
    private String name;
    private String sourceName;
    private Set<ItemPropertyDTO> itemProperties;

    public ItemDTO(Item item, boolean setItemProperties) {
        setName(item.getName());
        setSourceName(item.getSource().getName());
        if (setItemProperties) {
            Set<ItemPropertyDTO> itemPropertyDTOSet = new HashSet<>();
            for (ItemProperty itemProperty : item.getItemProperties()) {
                ItemPropertyDTO itemPropertyDTO = new ItemPropertyDTO(itemProperty, false);
                itemPropertyDTOSet.add(itemPropertyDTO);
            }
            setItemProperties(itemPropertyDTOSet);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Set<ItemPropertyDTO> getItemProperties() {
        return itemProperties;
    }

    public void setItemProperties(Set<ItemPropertyDTO> itemProperties) {
        this.itemProperties = itemProperties;
    }
}
