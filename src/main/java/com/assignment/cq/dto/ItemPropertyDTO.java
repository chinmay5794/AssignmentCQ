package com.assignment.cq.dto;

import com.assignment.cq.model.data.ItemProperty;

public class ItemPropertyDTO {
    private String name;
    private String value;
    private ItemDTO itemDTO;

    public ItemPropertyDTO(ItemProperty itemProperty, boolean setItem) {
        if (setItem) {
            setItemDTO(new ItemDTO(itemProperty.getItem(), false));
        }
        setName(itemProperty.getName());
        setValue(itemProperty.getValue());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ItemDTO getItemDTO() {
        return itemDTO;
    }

    public void setItemDTO(ItemDTO itemDTO) {
        this.itemDTO = itemDTO;
    }
}
