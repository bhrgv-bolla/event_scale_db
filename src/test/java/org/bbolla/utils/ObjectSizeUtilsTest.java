package org.bbolla.utils;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ObjectSizeUtilsTest {

    @Test
    public void testsObjectSize() {
        List<String> sameStrings = Lists.newArrayList();
        for (int i = 0; i < 9000000; i++) {
            sameStrings.add("somestring great happpenededdd");
        }
        long size = ObjectSizeUtils.findSize(sameStrings);
        log.info("Size of the object: {} bytes => {} KB", size, size / 1000);
        log.info("This object size calculator makes sense :D");
    }

    @Test
    public void testsSetIntersection() {
        Random rand = new Random();
        Set<Integer> set1 = Sets.newHashSet();
        Set<Integer> set2 = Sets.newHashSet();
        Set<Integer> set3 = Sets.newHashSet();
        for(int i=0; i< 1000000; i++) {
            set1.add(rand.nextInt(5000000));
            set2.add(rand.nextInt(5000000));
            set3.add(rand.nextInt(5000000));
        }
        Stopwatch stopwatch = Stopwatch.createStarted();
        Set<Integer> intersect = SetUtils.intersection(set1, set2, set3);
        stopwatch.stop();
        log.info("Set1: {}, Set2: {}, Set3: {}, Intersection : {}", set1.size(), set2.size(), set3.size(), intersect.size());
        log.info("Took: {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
}
