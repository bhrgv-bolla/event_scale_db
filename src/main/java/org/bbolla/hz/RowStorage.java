package org.bbolla.hz;


import com.google.common.collect.Lists;
import com.hazelcast.core.IMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Row Storage Manager - Takes care of storing the entire rows.
 */
public class RowStorage {

    private Server server;

    private IMap<Long, String> rowMap; //IMap detail is needed here to get more than one key at once.

    RowStorage(Server server) {
        this.server = server;
        this.rowMap = server.hz().getMap("rowMap");
    }

    List<String> getRows(Set<Long> ids) {
        if (ids.size() > 1000) {
            throw new UnsupportedOperationException("Currently max rows that can be retrieved are 1000 |" +
                    " narrow your search; requested : " + 1000);
        }
        Map<Long, String> result = rowMap.getAll(ids);
        return Lists.newArrayList(result.values());
    }

    void save(Long rowId, String row, String[] partitionKey) {
        //TODO today partition key is not being used.
        rowMap.put(rowId, row);
    }
}
