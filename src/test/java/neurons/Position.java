package neurons;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.labviros.is.Message;
import com.labviros.is.ServiceClient;
import com.labviros.is.msgs.common.Status;
import com.labviros.is.msgs.geometry.Point;
import com.labviros.is.msgs.robot.Pose;

import br.ufes.inf.lprm.situation.Role;
import neurons.core.AbstractNeuronsTopology;
import neurons.core.AbstractPlatform;
import neurons.core.Robot;
import neurons.sa.scene.MotorNeuron;
import neurons.stream.storm.NeuronsTopology;


//@Publish(host="host", port=4040)
public class Position extends MotorNeuron {
	
	public Position() {
		super();
		AbstractNeuronsTopology.platform.getConn().assertExchange("sa-data");
	}
	
	@Role(label = "f1")
	private Robot robot;

	public void setRobot(Robot robot) {
		this.robot = robot;
	}

	public Robot getRobotf() {
		return robot;
	}
	@Override
	public void setActive() {
		super.setActive();
		try {
			Status status = new Status();
			status.setValue("Ativado");
			status.setWhy(this.robot.getName() + " alcançou a coordenada x = " 
			+ this.robot.getPose().getPosition().getX());
			AbstractNeuronsTopology.platform.getConn().publish("sa-data", "robotAtPosition.info", status);
			
			
			Robot robot = (Robot) AbstractNeuronsTopology.platform.getDeviceInFocus();
			Pose p = new Pose();
			p.setPosition(new Point(1, 1, 1));
			p.setHeading(2);
			robot.setPose(p);
			
			//add an info
			
			ServiceClient client = robot.getPlatform().getConn().client();
			client = AbstractNeuronsTopology.platform.getConn().client();
			
	        String id =  client.request("rob1.set_pose", robot.getPose());
	        Message reply = client.receiveDiscardOthers(id, 3, TimeUnit.SECONDS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(robot.getName() + ": at position activated at " + new SimpleDateFormat("H:mm:ss").format(  new Date( this.getActivation().getTimestamp() ) ) );		
	}
	@Override
	public void setInactive() {
		super.setInactive();
		Status status = new Status();
		status.setValue("Desativado");
		try {
			status.setWhy(this.robot.getName() + " alcançou a coordenada x = " 
			+ this.robot.getPose().getPosition().getX());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			AbstractNeuronsTopology.platform.getConn().publish("sa-data", "robotAtPosition.info", status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(robot.getName() + ": at position deactivated at " + new SimpleDateFormat("H:mm:ss").format(  new Date( this.getDeactivation().getTimestamp() ) )  + ". It lasted: " + TimeUnit.MILLISECONDS.toSeconds( (this.getDeactivation().getTimestamp() - this.getActivation().getTimestamp()) ) + " seconds" );		
	}	
}
