package neurons.platform.visiot;

public class RethinkDBConfiguration {
    // connect to docker
    private static String DBHOST = "localhost";

    
    public RethinkDBConfiguration(String Dbhost) throws Exception {
    	DBHOST = Dbhost;
    	this.dbInitializer();
	}
    
    public static RethinkDBConnectionFactory connectionFactory() {
        return new RethinkDBConnectionFactory(DBHOST);
    }
    
    DbInitializer dbInitializer() throws Exception {
        return new DbInitializer();
    }
}