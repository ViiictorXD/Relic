package io.github.viiictorxd.relic;

import io.github.viiictorxd.relic.annotation.Table;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelicUtil {

    private static final List<Class<?>> NON_DATA_TYPE_LENGTH_CLASSES = Arrays.asList(
      Integer.class, int.class,
      Double.class, double.class,
      Boolean.class, boolean.class
    );

    public static final Map<Class<?>, Object> DEFAULT_DATA_TYPE_INSTANCES = new HashMap<>(){{
       put(String.class, "");
       put(Double.class, 0d);
       put(double.class, 0d);
       put(Integer.class, 0);
       put(int.class, 0);
       put(Float.class, 0f);
       put(float.class, 0f);
       put(Boolean.class, false);
       put(boolean.class, false);
    }};

    public static boolean needDataTypeLength(Class<?> clazz) {
        return !NON_DATA_TYPE_LENGTH_CLASSES.contains(clazz);
    }

    public static int getParametersCount(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredConstructors())
          .mapToInt(Constructor::getParameterCount)
          .sum();
    }

    public static <O> O createEmptyObject(Class<O> clazz) {
        try {
            int parametersCount = getParametersCount(clazz);

            Class<?>[] classes = new Class[parametersCount];
            Object[] objects = new Object[parametersCount];

            for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
                for (int index = 0; index < constructor.getParameters().length; index++) {
                    Parameter parameter = constructor.getParameters()[index];

                    classes[index] = parameter.getType();
                    objects[index] = DEFAULT_DATA_TYPE_INSTANCES.get(parameter.getType());
                }
            }

            Constructor<O> constructor = clazz.getConstructor(classes);

            return constructor.newInstance(objects);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static String getTableName(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);

        return (table == null || table.name().isEmpty())
          ? clazz.getSimpleName()
          : table.name();
    }

    public static Method[] getDeclaredMethods(Class<?> clazz) {
        return clazz.getDeclaredMethods();
    }

    public static Field[] getDeclaredFields(Class<?> clazz) {
        return clazz.getDeclaredFields();
    }
}
