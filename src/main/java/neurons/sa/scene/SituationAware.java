package neurons.sa.scene;

public class SituationAware {
	
	private String name;
	private String packageName;
	private Class<MotorNeuron> motorNeuron;
	private Fact fact;
	
	public SituationAware(Object defineClass){
		this.packageName = defineClass.getClass().getPackage().getName();
		packageName = packageName.replace('.', '/') + "/";
	}

	public void addFact(Fact fact){
		this.fact = fact;
	}
	
	public Fact getFact(){
		return this.fact;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<MotorNeuron> getMotorNeuron() {
		return motorNeuron;
	}

	public void setMotorNeuron(Class class1) {
		this.motorNeuron = class1;
	}

	public String getPackageName() {
		return packageName;
	}	
}