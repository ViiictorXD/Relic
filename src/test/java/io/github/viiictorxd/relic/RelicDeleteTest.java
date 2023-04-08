package io.github.viiictorxd.relic;

import io.github.viiictorxd.relic.object.MyObject;
import io.github.viiictorxd.relic.procedure.Procedure;
import org.junit.jupiter.api.Test;

public class RelicDeleteTest {

    @Test
    public void delete() {
        Relic relic = Relic.inst();

        Procedure<MyObject> myObjectProcedure = relic.generateProcedure(MyObject.class);

        MyObject myObject = new MyObject("Victor", 25, "RJ");

        myObjectProcedure.delete(myObject);
    }
}
