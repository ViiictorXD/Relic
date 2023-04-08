package io.github.viiictorxd.relic.procedure.query;

import io.github.viiictorxd.relic.adapter.TypeAdapterRegistry;
import io.github.viiictorxd.relic.procedure.query.generator.*;

import java.util.HashMap;
import java.util.Map;

public class ProcedureQueryGeneratorRegistry {

    private final Map<ProcedureQueryType, ProcedureQueryGenerator<?>> cachedProcedures
      = new HashMap<>();

    private final TypeAdapterRegistry typeAdapterRegistry;

    public ProcedureQueryGeneratorRegistry(TypeAdapterRegistry typeAdapterRegistry) {
        this.typeAdapterRegistry = typeAdapterRegistry;
    }

    public <T> ProcedureQueryGenerator<T> getQueryGenerator(ProcedureQueryType type) {
        return (ProcedureQueryGenerator<T>) cachedProcedures.get(type);
    }

    public void registerQueryGenerator(ProcedureQueryType type, ProcedureQueryGenerator<?> queryGenerator) {
        cachedProcedures.put(type, queryGenerator);
    }

    public void registerDefault() {
        registerQueryGenerator(ProcedureQueryType.INSERT, new InsertQueryGenerator<>(typeAdapterRegistry));
        registerQueryGenerator(ProcedureQueryType.DELETE, new DeleteQueryGenerator<>(typeAdapterRegistry));
        registerQueryGenerator(ProcedureQueryType.FIND, new SelectQueryGenerator<>());
        registerQueryGenerator(ProcedureQueryType.FIND_ALL, new SelectAllQueryGenerator<>());
        registerQueryGenerator(ProcedureQueryType.UPDATE, new UpdateQueryGenerator<>(typeAdapterRegistry));
        registerQueryGenerator(ProcedureQueryType.CREATE_TABLE, new CreateTableQueryGenerator<>(typeAdapterRegistry));
    }

    public enum ProcedureQueryType {

        /* C */ INSERT,
        /* R */ FIND, FIND_ALL,
        /* U */ UPDATE,
        /* D */ DELETE,
        /* ? */ CREATE_TABLE
    }
}
