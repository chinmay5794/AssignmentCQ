package com.assignment.cq.services;

import com.assignment.cq.model.PropertyType;
import com.assignment.cq.model.data.Item;
import com.assignment.cq.model.data.ItemProperty;
import com.assignment.cq.model.sourceparsing.DataSource;
import com.assignment.cq.repositories.ItemPropertyRepository;
import com.assignment.cq.repositories.ItemRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ItemsServiceImpl implements ItemsService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemPropertyRepository itemPropertyRepository;

    private Item getOrCreateItem(String itemName, DataSource source) {
        List<Item> itemList = itemRepository.findByNameAndSource_Name(itemName, source.getName());
        Item item = null;
        if (itemList != null && itemList.size() > 0) {
            item = itemList.get(0);
        } else {
            item = new Item();
            item.setSource(source);
            item.setName(itemName);
            item = itemRepository.save(item);
        }
        return item;
    }

    private void updateOrCreateItemProperty(Item item, String propertyName, String propertyValue) {
        List<ItemProperty> itemPropertyList = itemPropertyRepository.findByNameAndItem_Name(propertyName, item.getName());
        ItemProperty itemProperty;
        if (itemPropertyList != null && itemPropertyList.size() > 0) {
            itemProperty = itemPropertyList.get(0);
            if (!String.valueOf(itemProperty.getValue()).equals(propertyValue)) {
                itemProperty.setValue(propertyValue);
                itemPropertyRepository.save(itemProperty);
            }
        } else {
            itemProperty = new ItemProperty();
            itemProperty.setName(propertyName);
            itemProperty.setValue(propertyValue);
            itemProperty.setItem(item);
        }
    }

    @Override
    @Transactional
    public void updateOrInsertItems(DataSource source, JsonArray items) {
        for (JsonElement itemJsonElem : items) {
            JsonObject itemJsonObj = itemJsonElem.getAsJsonObject();

            if (!itemJsonObj.has("name")) {
                throw new IllegalStateException("Name field has not been extracted");
            }

            String itemName = itemJsonObj.get("name").getAsString();
            Item item = getOrCreateItem(itemName, source);

            for (Map.Entry<String, JsonElement> entry : itemJsonObj.entrySet()) {
                if ("name".equals(entry.getKey())) {
                    continue;
                }
                String propertyName = entry.getKey();
                String propertyValue = entry.getValue().getAsString();
                updateOrCreateItemProperty(item, propertyName, propertyValue);
            }
        }
    }
}
