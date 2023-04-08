package io.github.viiictorxd.relic;

import io.github.viiictorxd.relic.object.MyObject;
import io.github.viiictorxd.relic.procedure.Procedure;
import org.junit.jupiter.api.Test;

public class RelicInsertTest {

    @Test
    public void insert() {
Relic relic = Relic.inst();

Procedure<MyObject> myObjectProcedure = relic.generateProcedure(MyObject.class);

MyObject myObject = new MyObject("Almir", 38, "MG");

myObjectProcedure.insert(myObject);
    }
}
