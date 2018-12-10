package neurons.core;

import java.io.Serializable;

import backtype.storm.generated.StormTopology;
import backtype.storm.topology.BoltDeclarer;
import backtype.storm.topology.SpoutDeclarer;

public abstract class AbstractNeuronsTopology implements Serializable {
	
	public static AbstractPlatform platform;
	
	public abstract SpoutDeclarer newSynapse(String id, ISensorialNeuron s);
	
	public abstract BoltDeclarer newInterNeuronGroup(String id, IInterNeuron i);
	
	public abstract StormTopology createNeurons();

}