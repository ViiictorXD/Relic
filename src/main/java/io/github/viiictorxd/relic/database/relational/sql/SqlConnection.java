package io.github.viiictorxd.relic.database.relational.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlConnection {

    Connection getConnection();

    boolean connect();

    default void disconnect() {
        Connection connection = getConnection();

        try {
            if (connection == null || connection.isClosed()) {
                return;
            }

            connection.close();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    default boolean hasConnection() {
        Connection connection = getConnection();

        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
