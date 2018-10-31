package org.bbolla.utils;

import com.twitter.common.objectsize.ObjectSizeCalculator;

public class ObjectSizeUtils {

    public static long findSize(Object o) {
        return ObjectSizeCalculator.getObjectSize(o);
    }
}
