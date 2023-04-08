package io.github.viiictorxd.relic.database.relational.sql;

import java.util.Arrays;

public enum SqlDataType {

    VARCHAR(String.class),
    INT(Integer.class, int.class),
    DOUBLE(Double.class, double.class),
    BOOLEAN(Boolean.class, boolean.class),
    FLOAT(Float.class, float.class);

    private final Class<?>[] classes;

    SqlDataType(Class<?>... classes) {
        this.classes = classes;
    }

    public Class<?>[] getClasses() {
        return classes;
    }

    public static SqlDataType getDataType(Class<?> clazz) {
        return Arrays.stream(values())
          .filter(sqlDataType -> Arrays.stream(sqlDataType.classes).toList().contains(clazz))
          .findAny()
          .orElse(null);
    }
}
