package io.github.viiictorxd.relic.database.relational.sql.remote;

import io.github.viiictorxd.relic.database.relational.sql.SqlProperty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection extends AbstractRemoteSqlConnection {

    public MysqlConnection(SqlProperty sqlProperty) {
        super(sqlProperty);
    }

    @Override
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        return connection;
    }

    @Override
    public boolean connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(String.format(
              "jdbc:mysql://%s/%s", property.getProperty("host"), property.getProperty("database")
            ), property.getProperty("username"), property.getProperty("password"));

            return true;
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
