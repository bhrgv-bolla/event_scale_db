package org.bbolla.hz.rest;

import org.bbolla.hz.DimensionManager;
import org.bbolla.hz.EventConsumer;
import org.bbolla.hz.QueryEngine;
import org.bbolla.hz.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bbolla on 10/31/18
 */
@RestController
public class EventDBRestController {

    @Autowired
    private EventConsumer eventConsumer;

    @Autowired
    private QueryEngine queryEngine;

    @Autowired
    private DimensionManager dm;

    @PostMapping("/write")
    public ResponseEntity<Object> newEvent(@RequestBody Record event) {
        eventConsumer.consume(event);
        return ResponseEntity.ok("");
    }

    @PostMapping("/read")
    public ResponseEntity<Object> query(@RequestBody QueryRequest request) {
        return ResponseEntity.ok(queryEngine.getRows(request.getFilters()));
    }

    @GetMapping("/dimensions")
    public ResponseEntity<Object> dimensions() {
        return ResponseEntity.ok(dm.dimensions());
    }
}
