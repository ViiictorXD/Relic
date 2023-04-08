package io.github.viiictorxd.relic;

import io.github.viiictorxd.relic.object.MyObject;
import io.github.viiictorxd.relic.procedure.Procedure;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RelicSelectAllTest {

    @Test
    public void selectAll() {
        Relic relic = Relic.inst();

        Procedure<MyObject> myObjectProcedure = relic.generateProcedure(MyObject.class);

        List<MyObject> all = myObjectProcedure.findAll();

        all.forEach(System.out::println);
    }
}
