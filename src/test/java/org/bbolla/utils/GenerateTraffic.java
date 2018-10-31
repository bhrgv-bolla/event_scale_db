package org.bbolla.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.bbolla.hz.Record;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author bbolla on 10/31/18
 */
@Slf4j
public class GenerateTraffic {

    static final List<String> dimensions = Lists.newArrayList();
    static final Random rand = new Random();
    static final RestTemplate restTemplate = new RestTemplate();

    static {
        for (int i=0; i<1000; i++) {
            dimensions.add("dimension-"+i);
        }
    }

    @Test
    public void generateTraffic() {
        for(int i=0; i<500000; i++) {
            Record record = generateFakeRecord();
            restTemplate.postForEntity("http://localhost:8080/write", record, String.class);
            log.info("Total Writes until now: {}", i);
        }
    }

    private Record generateFakeRecord() {
        Record record = new Record();
        Map<String, String> event = Maps.newHashMap();
        record.setEvent(event);
        record.setIndexExclusions(Sets.newHashSet());
        int dimensionsInThisEvent = rand.nextInt(10) + 1;
        for(int i=0; i<dimensionsInThisEvent; i++) {
            String fakeDimension = dimensions.get(rand.nextInt(dimensions.size()));
            String dimensionVal = fakeDimension+"-val-"+rand.nextInt(100); //cardinality
            event.put(fakeDimension, dimensionVal);
        }
        return record;
    }
}
