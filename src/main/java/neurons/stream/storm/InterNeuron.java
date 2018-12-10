package neurons.stream.storm;

import java.util.Map;
import org.drools.KnowledgeBase;
import org.drools.RuntimeDroolsException;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import br.ufes.inf.lprm.scene.SituationKnowledgeBaseFactory;
import br.ufes.inf.lprm.scene.SituationKnowledgeBuilderFactory;
import br.ufes.inf.lprm.scene.base.listeners.SCENESessionListener;
import neurons.core.AbstractPlatform;
import neurons.core.IInterNeuron;
import neurons.sa.scene.RuleEngineThread;

public abstract class InterNeuron extends BaseBasicBolt implements IInterNeuron {	
	protected StatefulKnowledgeSession ksession = null;
	protected FactHandle fh1;
	protected AbstractPlatform platform;
	
	//folder/file.drl
	protected String drlResource;
	
	protected KnowledgeBase readKnowledgeBase() throws Exception {
    	
    	KnowledgeBuilder kbuilder = SituationKnowledgeBuilderFactory.newKnowledgeBuilder();	  
    	
        kbuilder.add(ResourceFactory.newClassPathResource(this.drlResource), ResourceType.DRL);        
        
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error: errors) {
                System.err.println(error);
            }
            throw new IllegalArgumentException("Could not parse knowledge.");
        }
        
        KnowledgeBase kbase = SituationKnowledgeBaseFactory.newKnowledgeBase(kbuilder);
        return kbase;
    }
	
	protected void startSA(){
		KnowledgeBase kbase;
		try {
			if (ksession == null){
				kbase = readKnowledgeBase();			
				ksession = kbase.newStatefulKnowledgeSession();
		        ksession.addEventListener(new SCENESessionListener());	          
		        final RuleEngineThread eng = new RuleEngineThread(ksession);
		        eng.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeDroolsException(e.getMessage());
			
		}
	}
	
	protected void AddFact(Object obj){
		fh1 = ksession.insert(obj);
	}
	
	protected void UpdateFact(Object obj){
		ksession.update(fh1, obj);
	}
	
	@Override
	public void prepare(Map config,
	                      TopologyContext context) {
		this.startSA();
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}	
}