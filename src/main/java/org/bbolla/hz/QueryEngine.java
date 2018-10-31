package org.bbolla.hz;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.bbolla.utils.SetUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class QueryEngine {

    private Indexer indexer;
    private RowStorage rowStorage;

    QueryEngine(Indexer indexer, RowStorage rowStorage) {
        this.indexer = indexer;
        this.rowStorage = rowStorage;
    }

    public List<Long> getRowIndices(Map<String, String> filters) { //TODO no pagination currently.
        List<Set<Long>> rows = Lists.newArrayList();
        filters.forEach((dim, dimVal) -> {
            Set<Long> indices = indexer.getRows(dim, dimVal);
            rows.add(indices);
        });
        return SetUtils.intersection(rows);
    }


    public List<String> getRows(Map<String, String> filters) {
        List<Long> indices = getRowIndices(filters);
        return rowStorage.getRows(Sets.newHashSet(indices));
    }
}
