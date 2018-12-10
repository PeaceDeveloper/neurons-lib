package neurons.sa.scene;

import org.drools.runtime.StatefulKnowledgeSession;

public class RuleEngineThread extends Thread {
	private StatefulKnowledgeSession ksession;
	public RuleEngineThread(StatefulKnowledgeSession ksession) {
		this.ksession = ksession;
	}
    public void run() {  	
    	this.ksession.fireUntilHalt(); 	
    }
}