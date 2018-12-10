package neurons.platform.visiot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeoutException;
import com.labviros.is.Message;
import com.labviros.is.ServiceClient;
import com.labviros.is.ServiceProvider;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Cursor;
import com.google.gson.Gson;
import com.labviros.is.*;
import neurons.core.AbstractPlatform;
import neurons.core.IConnection;
import neurons.core.Neurons;
import neurons.core.Robot;
import neurons.core.ServiceDelivery;

public class VisIoTConnection implements IConnection {
	
	private Connection conn;
	private static final RethinkDB r = RethinkDB.r;
	private RethinkDBConnectionFactory connectionFactory;
	private VisIoTPlatform visiotPlatform = new VisIoTPlatform();		
	
	public VisIoTConnection(String pathToProject) {		
		Neurons n = null;
		try {
			n = Neurons.newNeuronsConfig(pathToProject);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		RethinkDBConfiguration condb;
		try {
			condb = new RethinkDBConfiguration(n.getDatasourceUrl());
			connectionFactory = condb.connectionFactory();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			//conexão com o rabbitmq
			conn = new Connection(n.getConnectionUrl());
			visiotPlatform.setConn(this);
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getDataExchange(){
		return conn.getDataExchange();
	}

	public void assertExchange(String name) {
		try {
			conn.assertExchange(name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void stop() throws IOException, TimeoutException {
		this.conn.stop();
	}

	
	public ArrayBlockingQueue<Message> subscribe(String exchange, String topic) throws IOException {
		return this.conn.subscribe(exchange, topic);
	}

	
	public void unsubscribe(String exchange, String topic) throws IOException {
		this.conn.unsubscribe(exchange, topic);
		
	}

	
	public void publish(String exchange, String topic, Message message) throws Exception {
		this.conn.publish(exchange, topic, message);
	}
	
	public void publishServiceDelivery(String topic, Message message) throws Exception {
		if (!this.visiotPlatform.getThigns().stream().anyMatch(service -> service.getName().equals(topic))) {
			try {
				r.db("neurons").tableCreate("service_delivery").run(connectionFactory.createConnection());
				//r.db("neurons").table("service_delivery").indexCreate("name").run(connectionFactory.createConnection());
			}catch(Exception ex){
				ex.printStackTrace();
			}
			r.db("neurons").table("service_delivery").insert(
				r.hashMap("name", topic)
			).run(connectionFactory.createConnection());
		}
		this.publish("sa-data", topic + ".info", message);
	}

	
	public <T> ServiceProvider<T> advertise(T impl) throws IOException {
		return this.conn.advertise(impl);
	}

	
	public ServiceClient client() {
		return this.conn.client();
	}
	
	public AbstractPlatform Map() {		
		Gson gson = new Gson();
		try{					
			Cursor cursor = r.db("neurons").table("robots")                
	                .run(connectionFactory.createConnection());			
			List obj = cursor.toList();
			String json = gson.toJson(obj);
			System.out.println(json);
			Robot[] robots = gson.fromJson(json, Robot[].class);
			for(Robot robot : robots){
				robot.setPlatform(visiotPlatform);
				PoseResource resource = new PoseResource(visiotPlatform.getConn(), robot);
				SpeedResource speedResource = new SpeedResource(visiotPlatform.getConn(), robot);
				robot.setPoseResource(resource);
				robot.setSpeedResource(speedResource);
				visiotPlatform.addThing(robot);
			}
			cursor = r.db("neurons").table("service_delivery")                
	                .run(connectionFactory.createConnection());
			obj = cursor.toList();
			json = gson.toJson(obj);
			System.out.println(json);
			ServiceDelivery[] services = gson.fromJson(json, ServiceDelivery[].class);
			for(ServiceDelivery service : services) {
				service.setPlatform(visiotPlatform);
				StatusResource resource = new StatusResource(visiotPlatform.getConn(), service);
				service.setStatusResource(resource);
				visiotPlatform.addThing(service);
			}
			visiotPlatform.setConn(this);			
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}		
		return visiotPlatform;
	}
}