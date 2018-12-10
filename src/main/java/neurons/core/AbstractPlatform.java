package neurons.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPlatform implements IManageDevice, Serializable{
	
	private String name;
	
	private IConnection conn;
	
	private List<AbstractThing> devices = new ArrayList<AbstractThing>();
	
	private AbstractThing deviceInFocus;
	
	public AbstractPlatform(String name, IConnection conn){
		this.name = name;
		this.conn = conn;
		//mapping the devices
	}
	
	public AbstractPlatform(){
		
	}
	
	public void addThing(AbstractThing device){
		devices.add(device);
	}
	
	public List<AbstractThing> getThigns(){
		return devices;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IConnection getConn() {
		return conn;
	}

	public void setConn(IConnection conn) {
		this.conn = conn;
	}

	public List<AbstractThing> getDevices() {
		return devices;
	}

	public void setDevices(List<AbstractThing> devices) {
		this.devices = devices;
	}

	public AbstractThing getDeviceInFocus() {
		return deviceInFocus;
	}

	public void setDeviceInFocus(AbstractThing deviceInFocus) {
		this.deviceInFocus = deviceInFocus;
	}
}