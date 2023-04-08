package io.github.viiictorxd.relic.adapter;

import io.github.viiictorxd.relic.adapter.datatypes.*;

import java.util.HashMap;
import java.util.Map;

public class TypeAdapterRegistry {
    
    private final Map<Class<?>, TypeAdapter<?>> cachedAdapters
      = new HashMap<>();
    
    public <T> TypeAdapter<T> getAdapter(Class<?> clazz) {
        return (TypeAdapter<T>) cachedAdapters.get(clazz);
    }
    
    public void registerAdapter(Class<?> clazz, TypeAdapter<?> adapter) {
        if (cachedAdapters.containsKey(clazz))
            return;
        
        cachedAdapters.put(clazz, adapter);
    }

    public void registerDefault() {
        registerAdapter(String.class, new StringAdapter());
        
        registerAdapter(Boolean.class, new BooleanAdapter());
        registerAdapter(boolean.class, new BooleanAdapter());
        
        registerAdapter(Double.class, new DoubleAdapter());
        registerAdapter(double.class, new DoubleAdapter());
        
        registerAdapter(Integer.class, new IntegerAdapter());
        registerAdapter(int.class, new IntegerAdapter());
        
        registerAdapter(Float.class, new FloatAdapter());
        registerAdapter(float.class, new FloatAdapter());
    }
}
