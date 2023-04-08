package io.github.viiictorxd.relic.database.relational.sql;

import java.sql.ResultSet;

public interface SqlAdapter<O> {

    default O readOrNull(ResultSet resultSet) {
        try {
            return read(resultSet);
        } catch (Exception ignored) {
            return null;
        }
    }

    O read(ResultSet resultSet);
}
