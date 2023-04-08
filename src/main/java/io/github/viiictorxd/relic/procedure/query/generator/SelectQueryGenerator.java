package io.github.viiictorxd.relic.procedure.query.generator;

import io.github.viiictorxd.relic.RelicUtil;
import io.github.viiictorxd.relic.annotation.Column;
import io.github.viiictorxd.relic.annotation.Id;
import io.github.viiictorxd.relic.procedure.query.AbstractProcedureQueryGenerator;

import java.lang.reflect.Field;

public class SelectQueryGenerator<O> extends AbstractProcedureQueryGenerator<O> {

    @Override
    public String generateQuery(O object) {
        return null;
    }

    @Override
    public String generateQuery(O object, Object objectId) {

        String tableName = RelicUtil.getTableName(object.getClass());
        String columnName = null;

        Field[] declaredFields = RelicUtil.getDeclaredFields(object.getClass());

        for (Field field : declaredFields) {
            Id id = field.getAnnotation(Id.class);
            Column column = field.getAnnotation(Column.class);

            if (id == null || column == null)
                continue;

            field.setAccessible(true);

            columnName = (column.name().isEmpty()) ? field.getName() : column.name();
            break;
        }

        builder.append("SELECT * FROM ")
          .append(tableName)
          .append(" WHERE ")
          .append(columnName)
          .append(" = ")
          .append("'")
          .append(objectId.toString())
          .append("' LIMIT 1");

        System.out.println(builder.toString());

        return builder.toString();
    }
}