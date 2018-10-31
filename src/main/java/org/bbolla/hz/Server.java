package org.bbolla.hz;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class Server implements AutoCloseable {

    private static HazelcastInstance instance;

    static {
        new Server();
    }

    private Server() {
        instance = Hazelcast.newHazelcastInstance();
        //TODO may be unnecessary to use this hook.
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    log.warn("Running shutdown hook for hazelcast");
                    instance.shutdown();
                })
        );
    }

    public final static HazelcastInstance getInstance() {
        return instance;
    }


    public static void main(String[] args) {
        Map<String, String> aMap = instance.getMap("aMap");
        aMap.put("first", "something");
        aMap.put("second", "something");


        for (Map.Entry<String, String> entry : aMap.entrySet()) {
            log.info("Entry :{}", entry);
        }

        //Important to close
        System.exit(0);
    }

    @Override
    public void close() throws Exception {
        instance.shutdown();
    }
}