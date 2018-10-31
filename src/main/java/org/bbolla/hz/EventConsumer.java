package org.bbolla.hz;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

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
