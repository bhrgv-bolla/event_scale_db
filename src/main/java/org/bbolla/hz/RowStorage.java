package org.bbolla.hz;


import java.util.Map;

/**
 * Row Storage Manager - Takes care of storing the entire rows.
 */
public class RowStorage {

    private Server server;

    private Map<Long, Row> rowMap;

    RowStorage(Server server) {
        this.rowMap = server.hz().getMap("rowMap");
    }

}
