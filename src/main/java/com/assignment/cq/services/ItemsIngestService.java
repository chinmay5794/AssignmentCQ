package com.assignment.cq.services;

import com.assignment.cq.model.sourceparsing.DataSource;
import com.google.gson.JsonArray;

public interface ItemsIngestService {
    void ingestItems(String sourceName, JsonArray items);
}
