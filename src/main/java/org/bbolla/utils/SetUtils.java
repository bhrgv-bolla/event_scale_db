package org.bbolla.utils;

import com.google.common.collect.Sets;

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
}
