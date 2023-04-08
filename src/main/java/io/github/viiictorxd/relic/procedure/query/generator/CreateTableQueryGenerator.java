package io.github.viiictorxd.relic.procedure.query.generator;

import io.github.viiictorxd.relic.RelicUtil;
import io.github.viiictorxd.relic.adapter.TypeAdapterRegistry;
import io.github.viiictorxd.relic.annotation.Column;
import io.github.viiictorxd.relic.annotation.Id;
import io.github.viiictorxd.relic.database.relational.sql.SqlDataType;
import io.github.viiictorxd.relic.procedure.query.AbstractProcedureQueryGenerator;

import java.lang.reflect.Field;

public class CreateTableQueryGenerator<O> extends AbstractProcedureQueryGenerator<O> {

    private final TypeAdapterRegistry adapterRegistry;

    public CreateTableQueryGenerator(TypeAdapterRegistry adapterRegistry) {
        this.adapterRegistry = adapterRegistry;
    }

    @Override
    public String generateQuery(O object) {
        String tableName = RelicUtil.getTableName(object.getClass());

        StringBuilder columnsBuilder = new StringBuilder();
        String primaryKeyColumnName = null;

        Field[] declaredFields = RelicUtil.getDeclaredFields(object.getClass());
        for (Field field : declaredFields) {
            Column column = field.getAnnotation(Column.class);
            SqlDataType dataType = SqlDataType.getDataType(field.getType());

            if (column == null || dataType == null)
                continue;

            field.setAccessible(true);

            String columnName = column.name().isEmpty() ? field.getName() : column.name();
            String columnDataType = dataType.toString();

            boolean needDataTypeLength = RelicUtil.needDataTypeLength(field.getType());
            boolean primaryKey = field.isAnnotationPresent(Id.class);

            if (primaryKey) primaryKeyColumnName = columnName;

            columnsBuilder.append(columnName)
              .append(" ")
              .append(columnDataType);

            if (needDataTypeLength) {
                columnsBuilder.append("(")
                  .append(column.length())
                  .append(")");
            }

            columnsBuilder.append(" ")
              .append(column.nullable() ? "NULL" : "NOT NULL");

            columnsBuilder.append(", ");
        }

        builder.append("CREATE TABLE IF NOT EXISTS ")
          .append(tableName)
          .append(" (")
          .append(columnsBuilder.substring(0, columnsBuilder.length() - 2))
          .append(primaryKeyColumnName == null ? "" : ", PRIMARY KEY(" + primaryKeyColumnName + ")")
          .append(")");

        return builder.toString();
    }
}
