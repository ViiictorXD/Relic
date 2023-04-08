package io.github.viiictorxd.relic;

import io.github.viiictorxd.relic.object.MyObject;
import io.github.viiictorxd.relic.procedure.Procedure;
import org.junit.jupiter.api.Test;

public class RelicSelectTest {

    @Test
    public void select() {
        Relic relic = Relic.inst();

        Procedure<MyObject> myObjectProcedure = relic.generateProcedure(MyObject.class);

        MyObject myObject = myObjectProcedure.find("Victor");

        System.out.println(myObject.toString());
    }
}
