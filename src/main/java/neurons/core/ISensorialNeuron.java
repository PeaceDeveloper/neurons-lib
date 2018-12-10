package neurons.core;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import com.labviros.is.Message;

import backtype.storm.topology.IRichSpout;

public interface ISensorialNeuron extends IRichSpout {

	List<Integer> emit(String valueBy) throws Exception;
	
	void configure(ArrayBlockingQueue<Message> queue);	
}