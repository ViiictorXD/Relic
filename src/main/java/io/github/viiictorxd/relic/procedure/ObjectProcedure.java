package io.github.viiictorxd.relic.procedure;

import io.github.viiictorxd.relic.RelicUtil;
import io.github.viiictorxd.relic.adapter.TypeAdapter;
import io.github.viiictorxd.relic.adapter.TypeAdapterRegistry;
import io.github.viiictorxd.relic.annotation.Column;
import io.github.viiictorxd.relic.database.relational.sql.SqlConnection;
import io.github.viiictorxd.relic.procedure.query.ProcedureQueryGenerator;
import io.github.viiictorxd.relic.procedure.query.ProcedureQueryGeneratorRegistry;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectProcedure<O> implements Procedure<O> {

    private final SqlConnection sqlConnection;

    private final ProcedureQueryGeneratorRegistry procedureQueryGeneratorRegistry;
    private final TypeAdapterRegistry typeAdapterRegistry;

    private final Class<O> clazz;

    public ObjectProcedure(
      SqlConnection sqlConnection,
      ProcedureQueryGeneratorRegistry procedureQueryGeneratorRegistry,
      TypeAdapterRegistry typeAdapterRegistry,
      Class<O> clazz
    ) {
        this.sqlConnection = sqlConnection;
        this.procedureQueryGeneratorRegistry = procedureQueryGeneratorRegistry;
        this.typeAdapterRegistry = typeAdapterRegistry;
        this.clazz = clazz;
    }

    @Override
    public O find(Object id) {
        O emptyObject = RelicUtil.createEmptyObject(clazz);

        Connection connection = sqlConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
          generateQuery(emptyObject, id, ProcedureQueryGeneratorRegistry.ProcedureQueryType.FIND)
        )) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    Field[] declaredFields = RelicUtil.getDeclaredFields(clazz);
                    for (Field field : declaredFields) {
                        Column column = field.getAnnotation(Column.class);

                        if (column == null)
                            continue;

                        String columnName = (column.name().isEmpty())
                          ? field.getName()
                          : column.name();

                        field.setAccessible(true);

                        TypeAdapter<Object> adapter = typeAdapterRegistry.getAdapter(field.getType());

                        field.set(emptyObject, adapter.adapt(resultSet.getObject(columnName)));
                    }
                    return emptyObject;
                }
            } catch (IllegalAccessException exception) {
                throw new RuntimeException(exception);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public List<O> findAll() {
        O emptyObject = RelicUtil.createEmptyObject(clazz);

        List<O> objects = new ArrayList<>();

        Connection connection = sqlConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
          generateQuery(emptyObject, ProcedureQueryGeneratorRegistry.ProcedureQueryType.FIND_ALL)
        )) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    emptyObject = RelicUtil.createEmptyObject(clazz);

                    Field[] declaredFields = RelicUtil.getDeclaredFields(clazz);
                    for (Field field : declaredFields) {
                        Column column = field.getAnnotation(Column.class);

                        if (column == null)
                            continue;

                        String columnName = (column.name().isEmpty())
                          ? field.getName()
                          : column.name();

                        field.setAccessible(true);

                        TypeAdapter<Object> adapter = typeAdapterRegistry.getAdapter(field.getType());

                        field.set(emptyObject, adapter.adapt(resultSet.getObject(columnName)));
                    }
                    objects.add(emptyObject);
                }
                return objects;
            } catch (IllegalAccessException exception) {
                throw new RuntimeException(exception);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return objects;
    }

    @Override
    public void insert(O object) {
        Connection connection = sqlConnection.getConnection();

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(generateQuery(object, ProcedureQueryGeneratorRegistry.ProcedureQueryType.INSERT));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void update(O object) {
        Connection connection = sqlConnection.getConnection();

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(generateQuery(object, ProcedureQueryGeneratorRegistry.ProcedureQueryType.UPDATE));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void delete(O object) {
        Connection connection = sqlConnection.getConnection();

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(generateQuery(object, ProcedureQueryGeneratorRegistry.ProcedureQueryType.DELETE));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void createTable() {
        Connection connection = sqlConnection.getConnection();

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
              generateQuery(RelicUtil.createEmptyObject(clazz), ProcedureQueryGeneratorRegistry.ProcedureQueryType.CREATE_TABLE));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private String generateQuery(O object, ProcedureQueryGeneratorRegistry.ProcedureQueryType type) {
        ProcedureQueryGenerator<Object> queryGenerator
          = procedureQueryGeneratorRegistry.getQueryGenerator(type);

        return queryGenerator.generateQuery(object);
    }

    private String generateQuery(O object, Object objectId, ProcedureQueryGeneratorRegistry.ProcedureQueryType type) {
        ProcedureQueryGenerator<Object> queryGenerator
          = procedureQueryGeneratorRegistry.getQueryGenerator(type);

        return queryGenerator.generateQuery(object, objectId);
    }
}
