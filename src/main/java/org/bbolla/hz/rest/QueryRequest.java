package org.bbolla.hz.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author bbolla on 10/31/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryRequest {
    private Map<String, String> filters;
}
