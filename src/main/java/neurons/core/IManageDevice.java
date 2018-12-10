package neurons.core;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

import neurons.lambda.transform.drl.LambdaToRDL.Property;

public interface IManageDevice extends Serializable {

	void addThing(AbstractThing device);
	
	List<AbstractThing> getThings();
	
	Robot createRobot(String name);

	Robot getAnyRobot(Predicate<AbstractThing> filter);
	
	ServiceDelivery createServiceDelivery(String name);
	
	ServiceDelivery getAnyServiceDelivery(Predicate<AbstractThing> filter);
}