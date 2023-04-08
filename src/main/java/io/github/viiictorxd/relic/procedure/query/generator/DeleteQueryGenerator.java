package io.github.viiictorxd.relic.procedure.query.generator;

import io.github.viiictorxd.relic.RelicUtil;
import io.github.viiictorxd.relic.adapter.TypeAdapterRegistry;
import io.github.viiictorxd.relic.adapter.TypeAdapter;
import io.github.viiictorxd.relic.annotation.Column;
import io.github.viiictorxd.relic.annotation.Id;
import io.github.viiictorxd.relic.procedure.query.AbstractProcedureQueryGenerator;

import java.lang.reflect.Field;

public class DeleteQueryGenerator<O> extends AbstractProcedureQueryGenerator<O> {

    private final TypeAdapterRegistry adapterRegistry;

    public DeleteQueryGenerator(TypeAdapterRegistry adapterRegistry) {
        this.adapterRegistry = adapterRegistry;
    }

    @Override
    public String generateQuery(O object) {
        String tableName = RelicUtil.getTableName(object.getClass());

        StringBuilder columnsBuilder = new StringBuilder();
        StringBuilder valuesBuilder = new StringBuilder();

        Field[] declaredFields = RelicUtil.getDeclaredFields(object.getClass());

        for (Field field : declaredFields) {
            Column column = field.getAnnotation(Column.class);
            Id id = field.getAnnotation(Id.class);

            if (column == null || id == null)
                continue;

            field.setAccessible(true);

            try {
                String columnName = (column.name().isEmpty())
                  ? field.getName()
                  : column.name();
                Object valueObject = field.get(object);

                TypeAdapter<Object> adapter = adapterRegistry.getAdapter(field.getType());

                if (adapter == null)
                    continue;

                Object adapt = adapter.adapt(valueObject);

                columnsBuilder.append(columnName);
                valuesBuilder.append("'")
                  .append(adapt)
                  .append("'");
            } catch (IllegalAccessException exception) {
                exception.printStackTrace();
            }
        }

        String columns = columnsBuilder.toString();
        String values = valuesBuilder.toString();

        builder.append("DELETE FROM ")
          .append(tableName)
          .append(" WHERE ")
          .append(columns)
          .append(" = ")
          .append(values);

        return builder.toString();
    }
}
