package io.github.viiictorxd.relic.adapter.datatypes;

import io.github.viiictorxd.relic.adapter.TypeAdapter;

public class DoubleAdapter implements TypeAdapter<Double> {

    @Override
    public Double adapt(Object obj) {
        try {
            return Double.parseDouble(String.valueOf(obj));
        } catch (NumberFormatException ignored) {
            return null;
        }
    }
}
