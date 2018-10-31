package org.bbolla.hz;

import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

public class DimensionManager {

    private Map<String, Integer> dimensionsMap;

    DimensionManager(Server server) {
        dimensionsMap = server.hz().getReplicatedMap("dimensions");
    }

    /**
     * Adds a new dimension.
     *
     * @param dimension
     */
    void addDimension(String dimension) {
        if (dimensionsMap.containsKey(dimension)) {
            dimensionsMap.put(dimension, dimensionsMap.get(dimension) + 1);
        } else {
            dimensionsMap.put(dimension, 1);
        }
    }

    /**
     * Return all current dimensions.
     *
     * @return
     */
    Set<String> dimensions() {
        return Sets.newHashSet(dimensionsMap.keySet());
    }


}
