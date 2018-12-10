package neurons.platform.visiot;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import com.labviros.is.Message;
import com.labviros.is.msgs.robot.Speed;
import neurons.core.AbstractThing;
import neurons.core.AbstractResource;
import neurons.core.IConnection;

public class SpeedResource extends AbstractResource<Speed> {
	
	public SpeedResource(IConnection conn, AbstractThing device) {
		super(conn, device);
		
	}

	@Override
	public ArrayBlockingQueue<Message> configure() throws IOException {
		VisIoTConnection connection = (VisIoTConnection)this.getConn();
		if (super.queue == null){
			queue = connection.subscribe
					(connection.getDataExchange(), this.getThing().getName() + "." + "speed");
		}		
		return queue;
	}

	@Override
	public Speed get() throws InterruptedException, Exception {
		return new Speed((Message) super.queue.take());
	}	
}