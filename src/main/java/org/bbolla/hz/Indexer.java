package org.bbolla.hz;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.hazelcast.core.MultiMap;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Dimension slice and dicing.
 */
public class Indexer {

    private Map<String, MultiMap<String, Long>> dimensionsMap; //dimension name => dimension value => rows

    private DimensionManager dimensionManager;
    private Server server;

    Indexer(Server server, DimensionManager dimensionManager) {
        Set<String> dimensions = dimensionManager.dimensions();
        this.dimensionManager = dimensionManager;
        this.server = server;
        dimensionsMap = Maps.newHashMap();
        for (String dimension : dimensions) { //for existing
            addDimensionToMap(dimension);
        }
    }

    private String valMap(String dimension) {
        return dimension + "ValueMap";
    }

    void recordDimensionOccurence(String dimensionName, String dimensionValue, long rowId) {
        MultiMap<String, Long> dimValueMap = dimensionsMap.get(dimensionName);
        if(dimValueMap == null) {
            dimensionManager.addDimension(dimensionName); // Tell dimension manager there is a new dimension to track.
            dimValueMap = addDimensionToMap(dimensionName);
        }
        dimValueMap.put(dimensionValue, rowId);
    }

    /**
     * Creates a brand new multimap for the dimension / retrieves an existing one and uses the local map to maintain a reference.
     * @param dimensionName
     * @return
     */
    private MultiMap<String, Long> addDimensionToMap(String dimensionName) {
        MultiMap<String, Long> valMap = server.hz().getMultiMap(valMap(dimensionName));
        dimensionsMap.put(dimensionName, valMap);
        return valMap;
    }

    /**
     * Returns all rowId's for a dimension value.
     * @param dimensionName
     * @param dimensionValue
     * @return
     */
    Set<Long> getRows(String dimensionName, String dimensionValue) {
        MultiMap<String, Long> dimValueMap = dimensionsMap.get(dimensionName);
        if(dimValueMap == null) return Sets.newHashSet();
        Collection<Long> rowIds = dimValueMap.get(dimensionValue);
        if(rowIds == null) return Sets.newHashSet(); //dim doesn't exist yet / may be not indexed?.
        return Sets.newHashSet(rowIds);
    }

    Map<String, Set<String>> localDims() {
        Map<String, Set<String>> dimensionVal = Maps.newHashMap();
        dimensionsMap.forEach(
                (k, v) -> {
                    dimensionVal.put(k, v.localKeySet());
                }
        );
        return dimensionVal;
    }
}
