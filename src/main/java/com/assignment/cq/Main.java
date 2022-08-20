package com.assignment.cq;

import com.assignment.cq.datafetchers.SimpleXPathBasedWebCrawler;
import com.assignment.cq.model.sourceparsing.DataResource;
import com.assignment.cq.model.sourceparsing.DataSource;
import com.assignment.cq.repositories.DataResourceRepository;
import com.assignment.cq.repositories.DataSourceRepository;
import com.assignment.cq.services.ItemsIngestService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication(scanBasePackages = "com.assignment.cq")
@EnableScheduling
@EnableJpaAuditing
public class Main implements CommandLineRunner {

    private static final Logger logger = LogManager.getLogger(Main.class);

    @Autowired
    private ItemsIngestService itemsIngestService;

    @Autowired
    private DataSourceRepository dataSourceRepository;

    @Autowired
    private DataResourceRepository dataResourceRepository;

    public static void main(String args[]) {
        logger.info("Hello World!");
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        DataSource dataSource = new DataSource();
        dataSource.setName("screwfix.com");
        dataSource = dataSourceRepository.save(dataSource);

        DataResource dataResource = new DataResource();
        dataResource.setResource("https://www.screwfix.com/c/heating-plumbing/pipe/cat831500");
        dataResource.setDataSource(dataSource);
        dataResourceRepository.save(dataResource);


        Map<String, String> properties = new HashMap<>();
        String resource = "https://www.screwfix.com/c/heating-plumbing/pipe/cat831500";
        // properties.put(SimpleXPathBasedWebCrawler.WEB_PAGE_URL_KEY, "https://www.screwfix.com/c/heating-plumbing/pipe/cat831500");
        properties.put(SimpleXPathBasedWebCrawler.LIST_XPATH_KEY, "(//div[@class='row flex-container']/div[contains(@id, 'product_box')])");
        Map<String, String> featureXPathStore = new HashMap<>();
        featureXPathStore.put("name", ".//a[contains(@id, 'product_description')]");
        featureXPathStore.put("price", ".//h4[contains(@id, 'product_list_price')]");
        featureXPathStore.put("rating", ".//span[contains(@title, 'Product rating')]/span");
        properties.put(SimpleXPathBasedWebCrawler.FEATURE_XPATH_STORE_KEY, new Gson().toJson(featureXPathStore));
        SimpleXPathBasedWebCrawler crawler = new SimpleXPathBasedWebCrawler();
        crawler.validateProperties(properties);
        try {
            JsonArray result = crawler.fetchData(resource, properties);
            logger.info(result);
            itemsIngestService.updateOrInsertItems(dataSource.getName(), result);
        } catch (Exception e) {
            logger.error("Exception !!", e);
        }
    }
}
