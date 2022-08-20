package com.assignment.cq.services;

import com.assignment.cq.model.sourceparsing.DataSource;
import com.google.gson.JsonArray;

public interface ItemsIngestService {
    void updateOrInsertItems(String sourceName, JsonArray items);
}
