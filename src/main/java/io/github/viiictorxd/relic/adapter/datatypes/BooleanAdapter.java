package io.github.viiictorxd.relic.adapter.datatypes;

import io.github.viiictorxd.relic.adapter.TypeAdapter;

public class BooleanAdapter implements TypeAdapter<Boolean> {

    @Override
    public Boolean adapt(Object obj) {
        try {
            return Boolean.valueOf(String.valueOf(obj));
        } catch (Exception ignored) {
            return null;
        }
    }
}
