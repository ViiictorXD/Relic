package io.github.viiictorxd.relic.procedure.query.generator;

import io.github.viiictorxd.relic.RelicUtil;
import io.github.viiictorxd.relic.adapter.TypeAdapterRegistry;
import io.github.viiictorxd.relic.adapter.TypeAdapter;
import io.github.viiictorxd.relic.annotation.Column;
import io.github.viiictorxd.relic.annotation.Gson;
import io.github.viiictorxd.relic.annotation.Id;
import io.github.viiictorxd.relic.procedure.query.AbstractProcedureQueryGenerator;

import java.lang.reflect.Field;

public class InsertQueryGenerator<O> extends AbstractProcedureQueryGenerator<O> {

    private final TypeAdapterRegistry adapterRegistry;

    public InsertQueryGenerator(TypeAdapterRegistry adapterRegistry) {
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

            if (column == null)
                continue;

            field.setAccessible(true);

            String columnName = (column.name().isEmpty())
              ? field.getName()
              : column.name();


            try {
                Object valueObject = field.get(object);
                TypeAdapter<Object> adapter = adapterRegistry.getAdapter(field.getType());

                if (adapter == null)
                    continue;

                Object adapt = adapter.adapt(valueObject);

                columnsBuilder.append(columnName)
                  .append(", ");
                valuesBuilder.append("'")
                  .append(adapt)
                  .append("', ");
            } catch (IllegalAccessException exception) {
                exception.printStackTrace();
            }
        }

        String columns = columnsBuilder.substring(0, columnsBuilder.length() - 2);
        String values = valuesBuilder.substring(0, valuesBuilder.length() - 2);

        builder.append("INSERT INTO ")
          .append(tableName)
          .append(" (")
          .append(columns)
          .append(") VALUES (")
          .append(values)
          .append(")");

        return builder.toString();
    }
}
