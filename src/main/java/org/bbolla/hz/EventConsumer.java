package org.bbolla.hz;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class EventConsumer {

    private Indexer indexer;

    private RowStorage rowStorage;
    private IDProvider idProvider;

    EventConsumer(Indexer indexer, RowStorage rowStorage, IDProvider idProvider) {
        this.indexer = indexer;
        this.rowStorage = rowStorage;
        this.idProvider = idProvider;
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

        server.close();

    }

    public void consume(Record record) {
        long id = idProvider.next();
        for (Map.Entry<String, String> event : record.getEvent().entrySet()) { //indexing always
            if (!record.getIndexExclusions().contains(event.getKey())) {
                //index it
                indexer.recordDimensionOccurence(event.getKey(), event.getValue(), id);
            }
        }
        rowStorage.save(id, record.asString(), null);
    }
}
