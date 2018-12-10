package neurons.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;

import com.labviros.is.Message;

public abstract class AbstractResource<T> implements Serializable {
	
	private IConnection conn;
	private AbstractThing thing;
	
	public AbstractResource(IConnection conn, AbstractThing thing){
		this.conn = conn;
		this.thing = thing;		
	}

	protected ArrayBlockingQueue<Message> queue;
	
	public abstract ArrayBlockingQueue<Message> configure() throws IOException;
	
	public abstract T get() throws InterruptedException, Exception;

	public IConnection getConn() {
		return conn;
	}

	public AbstractThing getThing() {
		return thing;
	}
}