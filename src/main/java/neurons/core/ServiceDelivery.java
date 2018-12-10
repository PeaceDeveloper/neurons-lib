package neurons.core;

import com.labviros.is.msgs.common.Status;

public class ServiceDelivery extends AbstractThing {
	private Status status;	
	private AbstractResource<Status> statusResource;
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public AbstractResource<Status> getStatusResource() {
		return statusResource;
	}
	public void setStatusResource(AbstractResource<Status> statusResource) {
		this.statusResource = statusResource;
	}
}