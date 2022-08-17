package com.assignment.cq.datafetchers;

import com.assignment.cq.model.sourceparsing.DataResourceFetcherProperty;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public interface IDataFetcher {
    void validateProperties(Map<String, String> properties);
    JsonArray fetchData(String resource, Map<String, String> properties) throws Exception;
}
