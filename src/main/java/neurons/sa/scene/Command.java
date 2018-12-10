package neurons.sa.scene;

import java.util.List;

public abstract class Command {	
	private List<MotorNeuron> neurons;	
	public abstract MotorNeuron newMotorNeuron(MotorNeuron motorNeuron);	
}