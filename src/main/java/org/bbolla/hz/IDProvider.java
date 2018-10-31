package org.bbolla.hz;

import com.hazelcast.core.IdGenerator;

public class IDProvider {

    private IdGenerator idGenerator;

    IDProvider(Server server) {
        idGenerator = server.hz().getFlakeIdGenerator("rowId");
    }

    /**
     * Returns the next Id.
     * @return
     */
    long next() {
        return idGenerator.newId();
    }

    /**
     * Reset the idGenerator to serve from 0
     */
    void reset() {
        idGenerator.init(0);
    }

}
