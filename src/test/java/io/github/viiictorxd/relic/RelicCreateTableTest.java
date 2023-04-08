package io.github.viiictorxd.relic;

import io.github.viiictorxd.relic.database.relational.sql.SqlProperty;
import io.github.viiictorxd.relic.database.relational.sql.remote.MysqlConnection;
import io.github.viiictorxd.relic.object.MyObject;
import io.github.viiictorxd.relic.procedure.Procedure;
import org.junit.jupiter.api.Test;

public class RelicCreateTableTest {

    @Test
    public void createTable() {
        Relic relic = Relic.inst();

        Procedure<MyObject> myObjectProcedure = relic.generateProcedure(MyObject.class);

        myObjectProcedure.createTable();
    }
}
