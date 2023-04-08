package io.github.viiictorxd.relic.adapter.datatypes;

import io.github.viiictorxd.relic.adapter.TypeAdapter;

public class IntegerAdapter implements TypeAdapter<Integer> {

    @Override
    public Integer adapt(Object obj) {
        try {
            return Integer.parseInt(String.valueOf(obj));
        } catch (NumberFormatException ignored) {
            return null;
        }
    }
}
