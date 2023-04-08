package io.github.viiictorxd.relic;

import io.github.viiictorxd.relic.adapter.TypeAdapterRegistry;
import io.github.viiictorxd.relic.database.relational.sql.SqlConnection;
import io.github.viiictorxd.relic.database.relational.sql.SqlProperty;
import io.github.viiictorxd.relic.database.relational.sql.remote.MysqlConnection;
import io.github.viiictorxd.relic.procedure.ObjectProcedure;
import io.github.viiictorxd.relic.procedure.Procedure;
import io.github.viiictorxd.relic.procedure.query.ProcedureQueryGeneratorRegistry;

public class Relic {

    private static final Relic relic;

    private final TypeAdapterRegistry typeAdapterRegistry;
    private final ProcedureQueryGeneratorRegistry procedureQueryGeneratorRegistry;

    private SqlConnection sqlConnection;

    public Relic() {
        this.typeAdapterRegistry = new TypeAdapterRegistry();
        this.typeAdapterRegistry.registerDefault();

        this.procedureQueryGeneratorRegistry = new ProcedureQueryGeneratorRegistry(typeAdapterRegistry);
        this.procedureQueryGeneratorRegistry.registerDefault();
    }

    public void setConnection(SqlConnection sqlConnection) {
        this.sqlConnection = sqlConnection;
    }

    public <O> Procedure<O> generateProcedure(Class<O> clazz) {
        return new ObjectProcedure<O>(sqlConnection, procedureQueryGeneratorRegistry, typeAdapterRegistry, clazz);
    }

    public SqlConnection getSqlConnection() {
        return sqlConnection;
    }

    public ProcedureQueryGeneratorRegistry getQueryGeneratorRegistry() {
        return procedureQueryGeneratorRegistry;
    }

    public static Relic inst() {
        return relic;
    }

    static {
        relic = new Relic();
    }
}
