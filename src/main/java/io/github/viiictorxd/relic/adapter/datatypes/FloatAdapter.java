package io.github.viiictorxd.relic.adapter.datatypes;

import io.github.viiictorxd.relic.adapter.TypeAdapter;

public class FloatAdapter implements TypeAdapter<Float> {

    @Override
    public Float adapt(Object obj) {
        try {
            return Float.parseFloat(String.valueOf(obj));
        } catch (Exception ignored) {
            return null;
        }
    }
}
