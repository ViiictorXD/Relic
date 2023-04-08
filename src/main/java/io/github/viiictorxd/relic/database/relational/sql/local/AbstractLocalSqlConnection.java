package io.github.viiictorxd.relic.database.relational.sql.local;

import io.github.viiictorxd.relic.database.relational.sql.SqlConnection;

import java.io.File;

public abstract class AbstractLocalSqlConnection implements SqlConnection {

    protected final File file;
    protected final String name;

    public AbstractLocalSqlConnection(File file, String name) {
        this.file = file;
        this.name = name;
    }
}
