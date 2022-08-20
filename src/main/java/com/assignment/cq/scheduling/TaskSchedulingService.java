package com.assignment.cq.scheduling;

import com.assignment.cq.datafetchers.IDataFetcher;
import com.assignment.cq.model.Property;
import com.assignment.cq.model.sourceparsing.*;
import com.assignment.cq.services.ItemsIngestService;
import com.google.gson.JsonArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TaskSchedulingService {

    private static final Logger logger = LogManager.getLogger(TaskSchedulingService.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private ItemsIngestService itemsIngestService;

    Map<Long, ScheduledFuture<?>> jobsMap = new HashMap<>();

    private JsonArray invokeSpringBeanDataFetcher(String resource, String dataFetcherClassPath,
                                             Map<String, String> suppliedProperties) throws Exception {
        IDataFetcher dataFetcherImpl = applicationContext.getBean(dataFetcherClassPath, IDataFetcher.class);
        dataFetcherImpl.validateProperties(suppliedProperties);
        return dataFetcherImpl.fetchData(resource, suppliedProperties);
    }

    private Runnable prepareRunnable(DataResourceFetcher dataResourceFetcher) {
        return new Runnable() {
            @Override
            public void run() {
                // get resource details
                // FIXME : Store ID of DataResourceFetcher, & refresh it always
                DataResource dataResource = dataResourceFetcher.getDataResource();
                String resource = dataResource.getResource();
                DataSource source = dataResource.getDataSource();

                // load properties
                Set<DataResourceFetcherProperty> properties =
                        dataResourceFetcher.getDataResourceFetcherProperties();
                Map<String, String> suppliedProperties = properties.stream()
                        .collect(Collectors.toMap(Property::getName, DataResourceFetcherProperty::getValue));

                // invoke data fetcher
                try {
                    DataFetcher dataFetcher = dataResourceFetcher.getDataFetcher();
                    if (DataFetcherType.SPRING_BEAN.equals(dataFetcher.getType())) {
                        JsonArray result = invokeSpringBeanDataFetcher(resource,
                                dataFetcher.getDataFetcherPath(), suppliedProperties);
                        // TODO: if chunk size is large, split into multiple transactions
                        itemsIngestService.ingestItems(source.getName(), result);
                    }
                    // TODO : handle other fetcher types
                } catch (Exception e) {
                    String errMsg = String.format("Job %s has failed", dataResourceFetcher.getId());
                    logger.error(errMsg, e);
                    // TODO : notify
                }
            }
        };
    }

    public void scheduleATask(DataResourceFetcher dataResourceFetcher) {
        logger.info(String.format("Scheduling job for data fetcher = %s, data resource = %s, cron expression = %s, id = %s",
                dataResourceFetcher.getDataFetcher().getDataFetcherPath(),
                dataResourceFetcher.getDataResource().getResource(),
                dataResourceFetcher.getCronExpression(),
                dataResourceFetcher.getId()));
        Runnable runnable = prepareRunnable(dataResourceFetcher);
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(runnable,
                new CronTrigger(dataResourceFetcher.getCronExpression(),
                        TimeZone.getTimeZone(dataResourceFetcher.getTimeZone())));
        jobsMap.put(dataResourceFetcher.getId(), scheduledTask);
    }

    public void removeScheduledTask(Long id) {
        ScheduledFuture<?> scheduledTask = jobsMap.get(id);
        if(scheduledTask != null) {
            scheduledTask.cancel(true);
            jobsMap.put(id, null);
        } else {
            throw new IllegalArgumentException(
                    String.format("No job with id %s is scheduled", id));
        }
    }
}
