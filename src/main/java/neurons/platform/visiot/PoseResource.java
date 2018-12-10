package neurons.platform.visiot;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import com.labviros.is.Message;
import com.labviros.is.msgs.robot.Pose;
import neurons.core.AbstractThing;
import neurons.core.AbstractResource;
import neurons.core.IConnection;

public class PoseResource extends AbstractResource<Pose> {

	public PoseResource(IConnection conn, AbstractThing device) {
		super(conn, device);
		
	}

	@Override
	public ArrayBlockingQueue<Message> configure() throws IOException {
		VisIoTConnection connection = (VisIoTConnection)this.getConn();
		if (super.queue == null){
			queue = connection.subscribe
					(connection.getDataExchange(), this.getThing().getName() + "." + "pose");
		}
		
		return queue;
	}

	@Override
	public Pose get() throws InterruptedException, Exception {
		return new Pose((Message) super.queue.take());
	}
	
}