package neurons.core;

public abstract class AbstractThing implements IThing {
	
	private String id;
	private String name;
	private AbstractPlatform platform;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public AbstractPlatform getPlatform() {
		return platform;
	}
	public void setPlatform(AbstractPlatform platform) {
		this.platform = platform;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}