package neurons.platform.visiot;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

public class RethinkDBConnectionFactory {
    private String host;

    public RethinkDBConnectionFactory(String host) {
        this.host = host;
    }

    public Connection createConnection() {
        System.out.println("Initializing RethinkDBConnectionFactory. Host: "+host);
        return RethinkDB.r.connection().hostname(host).connect();
    }
}