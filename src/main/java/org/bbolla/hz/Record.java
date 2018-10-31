package org.bbolla.hz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    private Map<String, String> event;
    private Set<String> indexExclusions; //unless excluded; all things are indexed. TODO how to mention which ones to be indexed.

    public String asString() {
        return JsonUtils.serialize(event);
    }

    public Map<String, String> fromString(String input) {
        return JsonUtils.convertToMap(input);
    }
}
