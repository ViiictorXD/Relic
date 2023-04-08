package io.github.viiictorxd.relic.procedure.query;

public interface ProcedureQueryGenerator<O> {

    String generateQuery(O object);

    default String generateQuery(O object, Object objectId) {
        return null;
    }
}
