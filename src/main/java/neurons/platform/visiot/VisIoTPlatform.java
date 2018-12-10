package neurons.platform.visiot;

import neurons.core.AbstractThing;
import neurons.core.Camera;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.labviros.is.msgs.common.Status;

import neurons.core.AbstractPlatform;
import neurons.core.IConnection;
import neurons.core.Robot;
import neurons.core.ServiceDelivery;
import neurons.lambda.transform.drl.LambdaToRDL;
import neurons.lambda.transform.drl.LambdaToRDL.Property;

public class VisIoTPlatform extends AbstractPlatform {

	public VisIoTPlatform(String name, IConnection conn) {
		super(name, conn);
	}
	
	public VisIoTPlatform(){
		super();
	}
	
	//cria uma câmera e adicona a lista de coisas geridas pela plataforma
	public Robot createRobot(String name) {
		Robot robot = new Robot();
		PoseResource resource = new PoseResource(this.getConn(), robot);
		SpeedResource speedResource = new SpeedResource(this.getConn(), robot);
		robot.setPoseResource(resource);
		robot.setSpeedResource(speedResource);
		robot.setName(name);
		robot.setPlatform(this);
		return robot;
	}
	
	//o getanyinvocar o createservicedelivery e fazer o filter com base no servicedeliverycriado
	//criar um serviço de sensibilidade à situação e adiciona a lista de coisas geridas pela plataforma
	public ServiceDelivery createServiceDelivery(String name) {
		ServiceDelivery service = new ServiceDelivery();
		StatusResource statusResource = new StatusResource(this.getConn(), service);
		service.setName(name);
		service.setStatusResource(statusResource);
		service.setPlatform(this);
		this.addThing(service);		
		return service;
	}
	
	public ServiceDelivery getAnyServiceDelivery(Predicate<AbstractThing> filter) {
		Predicate<AbstractThing> filterCompose = service -> service.getClass() == ServiceDelivery.class;
		if (filter != null)
			filterCompose = filterCompose.and(filter);
		List<AbstractThing> things = this.getThigns();
		Optional<AbstractThing> optional = things.stream().filter(filterCompose).findFirst();
		if (optional.isPresent())
			return (ServiceDelivery)optional.get();
		else {
			/*
		    Status status = new Status();
			status.setValue("publishing");
			LambdaToRDL.init();
			String translateFilter = LambdaToRDL.getDRLCommand(filter);
			Pattern p = Pattern.compile("\"([^\"]*)\"");
			Matcher m = p.matcher(translateFilter);
			String serviceName = null;
			while (m.find()) {
				serviceName = m.group(1);
				System.out.println(serviceName);
				status.setWhy(String.format("Publicando serviço %s", serviceName));			  
			}			
			return this.createServiceDelivery(serviceName);
			*/
			//TODO: Remover esse hardcorde e fazer funcionar
			return this.createServiceDelivery("bbStatusService");
		}
	}
	
	//retorna um robô com base no filtro
	public Robot getAnyRobot(Predicate<AbstractThing> filter) {
		Predicate<AbstractThing> filterCompose = rob -> rob.getClass() == Robot.class;
		if (filter != null)
			filterCompose = filterCompose.and(filter);
		List<AbstractThing> things = this.getThigns();
		Optional<AbstractThing> optional = things.stream().filter(filterCompose).findFirst();
		if (optional.isPresent())
			return (Robot)optional.get();
		else
			return null;
	}
	
	//cria uma câmera e adicona a lista de coisas geridas pela plataforma
	public Camera createCamera(String name) {
		Camera camera = new Camera();
		camera.setName(name);
		camera.setPlatform(this);
		this.addThing(camera);
		return camera;
	}
	
	public void addThing(AbstractThing device){
		super.addThing(device);
		//regra específica da plataforma
	}

	@Override
	public List<AbstractThing> getThings() {
		return super.getThigns();
	}
}