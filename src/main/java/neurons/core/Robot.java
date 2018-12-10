package neurons.core;

import java.util.concurrent.TimeUnit;
import com.labviros.is.Message;
import com.labviros.is.ServiceClient;
import com.labviros.is.msgs.common.Status;
import com.labviros.is.msgs.geometry.Point;
import com.labviros.is.msgs.robot.Pose;
import com.labviros.is.msgs.robot.Speed;

public class Robot extends AbstractThing {
	private Pose pose;
	private Speed speed;
	private Status status;
	private AbstractResource<Pose> poseResource;
	private AbstractResource<Speed> speedResource;
	private AbstractResource<Status> statusResource;
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public AbstractResource<Status> getStatusResource() {
		return statusResource;
	}
	public void setStatusResource(AbstractResource<Status> statusResource) {
		this.statusResource = statusResource;
	}
	
	public Pose getPose() {
		return pose;
	}
	public void setPose(Pose pose) {
		this.pose = pose;
	}
	public Speed getSpeed() {
		return speed;
	}
	public void setSpeed(Speed speed) {
		this.speed = speed;
	}	
	
	public AbstractResource<Pose> getPoseResource() {
		return poseResource;
	}
	public void setPoseResource(AbstractResource<Pose> poseResource) {
		this.poseResource = poseResource;
	}
	
	public AbstractResource<Speed> getSpeedResource(){
		return speedResource;
	}
	
	public void setSpeedResource(AbstractResource<Speed> speedResource) {
		this.speedResource = speedResource;
	}
	
	public Message sendPose(double x , double y, double z, double heading) throws Exception {
		Pose p = new Pose();
		p.setPosition(new Point(x, y, z));
		p.setHeading(heading);		
		ServiceClient client = this.getPlatform().getConn().client();				
        String id =  client.request(this.getName() + ".set_pose", p);
        Message reply = client.receiveDiscardOthers(id, 3, TimeUnit.SECONDS);
        return reply;
	}
	public Message sendVelocity(double x) throws Exception {
		Speed speed = new Speed();
		speed.setAngular(x);
		ServiceClient client = this.getPlatform().getConn().client();				
        String id =  client.request(this.getName() + ".set_speed", speed);
        Message reply = client.receiveDiscardOthers(id, 3, TimeUnit.SECONDS);
        return reply;
	}
}