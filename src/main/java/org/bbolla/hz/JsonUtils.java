package org.bbolla.hz;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonUtils {

    private static final ObjectMapper om = new ObjectMapper();

    public static String serialize(Object val) {
        try {
            return om.writeValueAsString(val);
        } catch (Exception e) {
            throw new RuntimeException("while serializing: ", e);
        }
    }

    public static Map<String, String> convertToMap(String input) {
        try {
            return om.readValue(input, new TypeReference<Map<String, String>>(){});
        } catch (Exception e) {
            throw new RuntimeException("while de-serializing: ", e);
        }
    }
}
