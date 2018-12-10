package neurons.sa.scene;

import com.labviros.is.msgs.robot.Pose;
import neurons.lambda.transform.drl.LambdaToRDL;
import neurons.lambda.transform.drl.LambdaToRDL.Property;

public class TestDroolsGenerator {

	public static void main(String[] args) {
		Pose x;
		Property<Pose> p = a -> !(a.getPosition().getX() == 2 || a.getPosition().getY() >= 4);
		System.out.println(new LambdaToRDL().getDRLCommand(p));
	}
}