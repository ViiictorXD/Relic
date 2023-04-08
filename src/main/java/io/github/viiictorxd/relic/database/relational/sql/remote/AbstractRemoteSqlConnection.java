package io.github.viiictorxd.relic.database.relational.sql.remote;

import io.github.viiictorxd.relic.database.relational.sql.SqlConnection;
import io.github.viiictorxd.relic.database.relational.sql.SqlProperty;

import java.sql.Connection;

public abstract class AbstractRemoteSqlConnection implements SqlConnection {

    protected final SqlProperty property;
    protected Connection connection;

    public AbstractRemoteSqlConnection(SqlProperty property) {
        this.property = property;
    }
}
