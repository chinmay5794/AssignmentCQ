package com.assignment.cq.services;

import com.assignment.cq.model.data.Item;
import com.assignment.cq.model.data.ItemProperty;
import com.assignment.cq.model.sourceparsing.DataSource;
import com.assignment.cq.repositories.DataSourceRepository;
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
public class ItemsIngestServiceImpl implements ItemsIngestService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemPropertyRepository itemPropertyRepository;

    @Autowired
    private DataSourceRepository dataSourceRepository;

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
            }
        } else {
            itemProperty = new ItemProperty();
            itemProperty.setName(propertyName);
            itemProperty.setValue(propertyValue);
            itemProperty.setItem(item);
        }
        itemPropertyRepository.save(itemProperty);
    }

    /**
     * Attaches supplied @items to source with given @sourceName
     * & updates or inserts them to the DB.
     * @param sourceName : Name
     * @param items : JSON array of item objects with their properties
     */
    @Override
    @Transactional
    public void updateOrInsertItems(String sourceName, JsonArray items) {

        List<DataSource> sourceList = dataSourceRepository.findByName(sourceName);
        DataSource source = null;
        if (sourceList != null && !sourceList.isEmpty()) {
            source = sourceList.get(0);
        } else {
            throw new IllegalArgumentException(String.format("Source with ID %s not found", sourceName));
        }

        for (JsonElement itemJsonElem : items) {
            JsonObject itemJsonObj = itemJsonElem.getAsJsonObject();

            /*
             * FIXME: handle ID field better
             *  Potential solution - Pass ID field as parameter from DataResourceFetcher
             */
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
