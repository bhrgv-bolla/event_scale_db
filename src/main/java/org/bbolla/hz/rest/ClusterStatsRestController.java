package org.bbolla.hz.rest;

import org.bbolla.hz.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bbolla on 10/31/18
 */
@RestController
@RequestMapping("/cluster/")
public class ClusterStatsRestController {

    @Autowired
    private Server server;

    @GetMapping("/stats")
    public ResponseEntity<Object> getClusterStats() {
        return ResponseEntity.ok(server.getStats());
    }
}
