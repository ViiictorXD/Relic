package io.github.viiictorxd.relic.procedure;

import java.util.List;

public interface Procedure<O> {

    O find(Object id);

    List<O> findAll();

    void insert(O object);

    void update(O object);

    void delete(O object);

    void createTable();
}
