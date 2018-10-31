package org.bbolla.hz;

import com.google.common.collect.Lists;
import com.hazelcast.config.Config;
import com.hazelcast.config.FlakeIdGeneratorConfig;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
public class Server implements AutoCloseable {

    private static HazelcastInstance instance;

    private static Server server;

    private Server() {
        Config config = new Config()
                .addFlakeIdGeneratorConfig(new FlakeIdGeneratorConfig("rowId").setPrefetchCount(5));

        //TODO management center.
        config.getManagementCenterConfig().setUrl("http://localhost:9000/hazelcast-mancenter/");
        config.getManagementCenterConfig().setEnabled(true);


        instance = Hazelcast.newHazelcastInstance(config);

        //TODO may be unnecessary to use this hook.
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    log.warn("Running shutdown hook for hazelcast");
                    instance.shutdown();
                })
        );
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

    final static Server getInstance() {
        if (server == null) server = new Server();
        return server;
    }

    final HazelcastInstance hz() {
        return instance;
    }

    final public Object getStats() {
        Collection<DistributedObject> allDistributedObjects = hz().getDistributedObjects();
        List<DistributedObjectStats> stats = Lists.newArrayList();
        for(DistributedObject distributedObject : allDistributedObjects) {
            String name = distributedObject.getName();
            String serviceName = distributedObject.getServiceName();
            stats.add(new DistributedObjectStats(name, serviceName));
        }
        return stats;
    }

    @Override
    public void close() throws Exception {
        instance.shutdown();
    }
}