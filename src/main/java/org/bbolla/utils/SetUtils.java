package org.bbolla.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SetUtils {

    public static <T> Set<T> intersection(Set<T> set1, Set<T> ...set2) {
        if(set2 == null) return Sets.newHashSet(set1);
        else {
            Set<T> current = set1;
            for(Set<T> next: set2) {
                current = Sets.intersection(current, next);
            }
            return current;
        }
    }

    public static <T extends Comparable> List<T> intersection(List<Set<T>> rows) {
        if(rows == null || rows.size() == 0) return Lists.newArrayList();
        else if(rows.size() == 1) return Lists.newArrayList(rows.get(0));
        else {
            Set<T> current = rows.get(0);
            for(Set<T> next: rows.subList(1, rows.size())) {
                current = Sets.intersection(current, next);
            }
            List<T> indices = Lists.newArrayList(current);
            Collections.sort(indices);
            return indices;
        }
    }
}
