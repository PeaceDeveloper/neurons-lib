package neurons.stream.storm;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.BoltDeclarer;
import backtype.storm.topology.SpoutDeclarer;
import backtype.storm.topology.TopologyBuilder;
import neurons.core.AbstractNeuronsTopology;
import neurons.core.IInterNeuron;
import neurons.core.ISensorialNeuron;

public class NeuronsTopology extends AbstractNeuronsTopology{
	
	TopologyBuilder builder = new TopologyBuilder();
	
	public SpoutDeclarer newSynapse(String id, ISensorialNeuron s){
		return builder.setSpout(id, s, null);
	}
	
	public BoltDeclarer newInterNeuronGroup(String id, IInterNeuron i) {
		return builder.setBolt(id, i, null);
	}
	
	public StormTopology createNeurons() {
        return builder.createTopology();
    }
}