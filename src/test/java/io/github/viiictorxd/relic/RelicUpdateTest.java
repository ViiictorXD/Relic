package io.github.viiictorxd.relic;

import io.github.viiictorxd.relic.object.MyObject;
import io.github.viiictorxd.relic.procedure.Procedure;
import org.junit.jupiter.api.Test;

public class RelicUpdateTest {

    @Test
    public void update() {
        Relic relic = Relic.inst();

        Procedure<MyObject> myObjectProcedure = relic.generateProcedure(MyObject.class);

        MyObject myObject = new MyObject("Victor", 30, "RJ");

        myObjectProcedure.update(myObject);
    }
}
