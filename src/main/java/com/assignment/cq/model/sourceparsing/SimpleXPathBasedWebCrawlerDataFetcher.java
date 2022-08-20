package com.assignment.cq.model.sourceparsing;

import com.google.gson.JsonArray;

import java.util.Map;

public class SimpleXPathBasedWebCrawlerDataFetcher extends DataFetcher {
    @Override
    public void validateProperties(Map<String, String> properties) {

    }

    @Override
    public JsonArray fetchData(String resource, Map<String, String> properties) throws Exception {
        return null;
    }
}
