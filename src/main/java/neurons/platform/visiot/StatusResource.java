package neurons.platform.visiot;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import com.labviros.is.Message;
import com.labviros.is.msgs.common.Status;
import neurons.core.AbstractThing;
import neurons.core.AbstractResource;
import neurons.core.IConnection;

public class StatusResource extends AbstractResource<Status> {

	public StatusResource(IConnection conn, AbstractThing thing) {
		super(conn, thing);
		
	}

	@Override
	public ArrayBlockingQueue<Message> configure() throws IOException {
		VisIoTConnection connection = (VisIoTConnection)this.getConn();
		if (super.queue == null){
			//fila de mensagens informativas relativas a 
			//sensibilidade à situação entregues a plataforma de middleware
			queue = connection.subscribe
					("sa-data", this.getThing().getName() + "." + "info");
		}		
		return queue;
	}

	@Override
	public Status get() throws InterruptedException, Exception {
		return new Status((Message) super.queue.take());
	}	
}