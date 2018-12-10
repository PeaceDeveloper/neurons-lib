package neurons.stream.storm;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import com.labviros.is.Message;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Values;
import neurons.core.ISensorialNeuron;

public abstract class SensorialNeuron extends BaseRichSpout implements ISensorialNeuron {
	
	protected ArrayBlockingQueue<Message> queue;
	protected SpoutOutputCollector outputCollector;	
	
	public List<Integer> emit(String valueBy) throws Exception{
		Message value = queue.take();
		return outputCollector.emit(new Values(valueBy, value), 
				value.getEnvelope().getDeliveryTag());
	}
	
	public void configure(ArrayBlockingQueue<Message> queue){
		this.queue = queue;
	}
}