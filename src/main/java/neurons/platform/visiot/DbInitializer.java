package neurons.platform.visiot;

import java.util.List;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

public class DbInitializer {
    
    private RethinkDBConnectionFactory connectionFactory;
    private static final RethinkDB r = RethinkDB.r;

    //@Override
    public DbInitializer() throws Exception {
        createDb();
        //chatChangesListener.pushChangesToWebSocket();
    }

    private void createDb() {
        Connection connection = RethinkDBConfiguration.connectionFactory()
        		.createConnection();
        List<String> dbList = r.dbList().run(connection);
        if (!dbList.contains("neurons")) {
            r.dbCreate("neurons").run(connection);
        }
        List<String> tables = r.db("neurons").tableList().run(connection);
        if (!tables.contains("robots")) {
            r.db("neurons").tableCreate("robots").run(connection);
            //r.db("neurons").table("robots").indexCreate("time").run(connection);
        }
    }
}