package io.github.viiictorxd.relic.procedure.query.generator;

import io.github.viiictorxd.relic.RelicUtil;
import io.github.viiictorxd.relic.adapter.TypeAdapter;
import io.github.viiictorxd.relic.adapter.TypeAdapterRegistry;
import io.github.viiictorxd.relic.annotation.Column;
import io.github.viiictorxd.relic.annotation.Gson;
import io.github.viiictorxd.relic.annotation.Id;
import io.github.viiictorxd.relic.procedure.query.AbstractProcedureQueryGenerator;

import java.lang.reflect.Field;

public class UpdateQueryGenerator<O> extends AbstractProcedureQueryGenerator<O> {

    private final TypeAdapterRegistry adapterRegistry;

    public UpdateQueryGenerator(TypeAdapterRegistry adapterRegistry) {
        this.adapterRegistry = adapterRegistry;
    }

    @Override
    public String generateQuery(O object) {
        String tableName = RelicUtil.getTableName(object.getClass());

        StringBuilder whereBuilder = new StringBuilder();
        StringBuilder valuesBuilder = new StringBuilder();

        Field[] declaredFields = RelicUtil.getDeclaredFields(object.getClass());

        for (Field field : declaredFields) {
            Id id = field.getAnnotation(Id.class);
            Column column = field.getAnnotation(Column.class);

            if (column == null)
                continue;

            field.setAccessible(true);

            String columnName = (column.name().isEmpty())
              ? field.getName()
              : column.name();

            Gson gson = field.getAnnotation(Gson.class);

            try {
                Object valueObject = field.get(object);
                if (gson == null) {
                    TypeAdapter<Object> adapter = adapterRegistry.getAdapter(field.getType());

                    if (adapter == null)
                        continue;

                    Object adapt = adapter.adapt(valueObject);

                    if (id == null) {
                        valuesBuilder.append(columnName)
                          .append(" = ")
                          .append("'")
                          .append(adapt)
                          .append("', ");
                    } else {
                        whereBuilder.append(columnName)
                          .append(" = ")
                          .append("'")
                          .append(adapt)
                          .append("'");
                    }
                } else {

                }
            } catch (IllegalAccessException exception) {
                exception.printStackTrace();
            }
        }

        String values = valuesBuilder.substring(0, valuesBuilder.length() - 2);
        String where = whereBuilder.toString();

        builder.append("UPDATE ")
          .append(tableName)
          .append(" SET ")
          .append(values)
          .append(" WHERE ")
          .append(where);

        return builder.toString();
    }
}