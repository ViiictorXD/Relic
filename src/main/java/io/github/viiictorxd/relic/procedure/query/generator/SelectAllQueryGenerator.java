package io.github.viiictorxd.relic.procedure.query.generator;

import io.github.viiictorxd.relic.RelicUtil;
import io.github.viiictorxd.relic.procedure.query.AbstractProcedureQueryGenerator;

public class SelectAllQueryGenerator<O> extends AbstractProcedureQueryGenerator<O> {

    @Override
    public String generateQuery(O object) {
        String tableName = RelicUtil.getTableName(object.getClass());

        builder.append("SELECT * FROM ")
          .append(tableName);

        return builder.toString();
    }
}