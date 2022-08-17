package com.assignment.cq.datafetchers;

import com.assignment.cq.model.sourceparsing.DataResourceFetcherProperty;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class SimpleXPathBasedWebCrawler implements IDataFetcher {

    public static final String LIST_XPATH_KEY = "list_xpath";
    public static final String FEATURE_XPATH_STORE_KEY = "feature_xpath";

    private static final Logger logger = LogManager.getLogger(SimpleXPathBasedWebCrawler.class);

    @Override
    public void validateProperties(Map<String, String> properties) {
        // check if none supplied
        if (properties == null || properties.isEmpty()) {
            throw new IllegalArgumentException("Properties not supplied");
        }

        // evaluate if mandatory properties are supplied
        List<String> mandatoryProperties = Arrays.asList(LIST_XPATH_KEY, FEATURE_XPATH_STORE_KEY);
        List<String> propertiesNotSupplied = new ArrayList<>();
        for (String property: mandatoryProperties) {
            if (!properties.containsKey(property) ||
                    properties.getOrDefault(property, "").trim().isEmpty()) {
                propertiesNotSupplied.add(property);
            }
        }

        if (!propertiesNotSupplied.isEmpty()) {
            throw new IllegalArgumentException(String.format("Properties not supplied - %s",
                    String.join(",", propertiesNotSupplied)));
        }


    }

    private JsonArray doFetchData(String url, String listXPath, Map<String, String> featureXPaths) throws IOException {
        // initialize web client
        WebClient webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false); // Cancel CSS support
        webClient.getOptions().setJavaScriptEnabled(false); // Cancel JavaScript support
        HtmlPage page = webClient.getPage(url);
        logger.info("Received HTML page successfully");

        // fetch elements
        JsonArray result = new JsonArray();
        List<HtmlElement> dataElements = page.getByXPath(listXPath);
        logger.info(String.format("Extracted %s elements with listXPath %s", dataElements.size(), listXPath));
        for (HtmlElement htmlElement : dataElements) {
            JsonObject item = new JsonObject();
            for (String featureName : featureXPaths.keySet()) {
                String featureXPath = featureXPaths.get(featureName);
                List<HtmlElement> featureElem = htmlElement.getByXPath(featureXPath);
                String featureValue = null;
                if (featureElem.size() > 0) {
                    featureValue = featureElem.get(0).getVisibleText();
                } else if (logger.isDebugEnabled()) {
                    logger.debug(String.format("Received no value for feature %s with XPath %s",
                            featureName, featureXPath));
                }
                item.addProperty(featureName, featureValue);
            }
            result.add(item);
        }
        return result;
    }

    @Override
    public JsonArray fetchData(String resource, Map<String, String> properties) throws Exception {

        logger.info("Fetching data - STARTED");

        String listXPath = properties.get(LIST_XPATH_KEY);
        String featureXPathStr = properties.get(FEATURE_XPATH_STORE_KEY);
        Map<String, String> featureXPathStore = new Gson().fromJson(featureXPathStr, new TypeToken<HashMap<String, String>>() {}.getType());

        logger.info(String.format("URL = %s", resource));
        logger.info(String.format("ListXPath = %s", listXPath));
        if (logger.isDebugEnabled()) {
            for (String featureName : featureXPathStore.keySet()) {
                logger.debug(String.format("%s XPath = %s", featureName, featureXPathStore.get(featureName)));
            }
        }

        JsonArray result = doFetchData(resource, listXPath, featureXPathStore);

        logger.info("Fetching data - COMPLETED");
        return result;
    }
}
