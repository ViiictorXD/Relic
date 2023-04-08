package io.github.viiictorxd.relic.adapter.datatypes;

import io.github.viiictorxd.relic.adapter.TypeAdapter;

public class StringAdapter implements TypeAdapter<String> {

    @Override
    public String adapt(Object obj) {
        return String.valueOf(obj);
    }
}
