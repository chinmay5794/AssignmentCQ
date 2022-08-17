package com.assignment.cq.services;

import com.assignment.cq.model.sourceparsing.DataSource;
import com.google.gson.JsonArray;

public interface ItemsService {
    void updateOrInsertItems(DataSource source, JsonArray items);
}
