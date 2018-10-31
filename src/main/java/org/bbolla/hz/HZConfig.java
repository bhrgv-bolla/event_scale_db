package org.bbolla.hz;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author bbolla on 10/31/18
 */
@Configuration
@Slf4j
public class HZConfig {


    @Bean
    public Server server() {
        return Server.getInstance();
    }

    @Bean
    public DimensionManager dimensionManager(Server server) {
        return new DimensionManager(server);
    }

    @Bean
    public Indexer indexer(Server server, DimensionManager dm) {
        return new Indexer(server, dm);
    }

    @Bean
    public RowStorage rowStorage(Server server) {
        return new RowStorage(server);
    }

    @Bean
    public IDProvider idProvider(Server server) {
        return new IDProvider(server);
    }

    @Bean
    public EventConsumer eventConsumer(Indexer indexer, RowStorage rowStorage, IDProvider idProvider) {
        return new EventConsumer(indexer, rowStorage, idProvider);
    }

    @Bean
    public QueryEngine queryEngine(Indexer indexer, RowStorage rowStorage) {
        return new QueryEngine(indexer, rowStorage);
    }


    public static void main(String[] args) throws Exception {
        //example code.

        //all initialization, wiring
        Server server = Server.getInstance();
        DimensionManager dm = new DimensionManager(server);
        Indexer indexer = new Indexer(server, dm);
        RowStorage rowStorage = new RowStorage(server);
        IDProvider idProvider = new IDProvider(server);
        EventConsumer eventConsumer = new EventConsumer(indexer, rowStorage, idProvider);
        QueryEngine queryEngine = new QueryEngine(indexer, rowStorage);

        //using event consumer
        Stopwatch stopwatch = Stopwatch.createUnstarted();
        Map<String, String> event = Maps.newHashMap();
        event.put("dim1", "dim1val1");
        event.put("dim2", "dim2val1");
        event.put("dim3", "dim3val1");
        event.put("dim4", "dim4val1");

        Map<String, String> event2 = Maps.newHashMap();
        event2.put("dim1", "dim1val3");
        event2.put("dim2", "dim2val1");
        event2.put("dim3", "dim3val3");

        //prepare query
        Map<String, String> filters = Maps.newHashMap();
        filters.put("dim2", "dim2val1");

        stopwatch.start();
        //results
        //consume the record
        eventConsumer.consume(new Record(event, Sets.newHashSet()));
        eventConsumer.consume(new Record(event2, Sets.newHashSet()));
        List<String> rows = queryEngine.getRows(filters);
        Set<String> dims = dm.dimensions();
        Object rowIdxs = indexer.getRows("dim2", "dim2val1");

        stopwatch.stop();

        log.info("All dimensions : {}", dims);
        log.info("indexes : {}", rowIdxs);
        log.info("queried rows: {}", rows);
        log.info("~~~~~~~~~~~~~~~~~~~Time Taken: {} ms~~~~~~~~~~~~~~~~~~~~", stopwatch.elapsed(TimeUnit.MILLISECONDS));


        log.info("Local to this node: {}", indexer.localDims());
//        server.close();

    }


}
