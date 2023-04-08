package io.github.viiictorxd.relic.database.relational.sql;

import java.util.HashMap;
import java.util.Map;

public class SqlProperty {

    private final Map<String, String> cachedProperties
      = new HashMap<>();

    public String getProperty(String key) {
        return cachedProperties.get(key);
    }

    public void addProperty(String key, String property) {
        cachedProperties.put(key, property);
    }
}
