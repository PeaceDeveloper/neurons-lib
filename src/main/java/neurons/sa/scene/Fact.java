package neurons.sa.scene;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import neurons.lambda.transform.drl.LambdaToRDL;
import neurons.lambda.transform.drl.LambdaToRDL.Property;

//Recurso, Entidade e Serviço
public class Fact<T, E, R> implements Cloneable {
	
	private String name;
	private Class<T> property;
	private Class<E> entity;	
	private Class<R> resource;
	private Property<? super T> whenClause;
	private String pathLambdaCompiledDRL;
	//um json para filtrar
	private String filterThing;	
	
	public Fact(String name, Class class1, String path){
		this.setName(name);
		this.setResource(class1);
		this.pathLambdaCompiledDRL = path + "/lambdaCompiledDRL.txt";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<R> getResource() {
		return resource;
	}

	public void setResource(Class<R> resource) {
		this.resource = resource;
	}

	public Class<E> getEntity() {
		return entity;
	}

	public void setEntity(Class<E> entity) {
		this.entity = entity;
	}
	
	public Class<T> getProperty() {
		return property;
	}

	public void setProperty(Class<T> property) {
		this.property = property;
	}

	public String getDroolsWhenClause() throws IOException {
		try {
			return FileUtils.readFileToString(new File(this.pathLambdaCompiledDRL));
		} catch (IOException e) {
			throw new IOException("Run a compiled main project is necessary!");
		}
	}
	
	public void setWhenClause(Property<? super T> whenClause, Boolean isWriteToFile) {
		this.whenClause = whenClause;
		if (isWriteToFile){
			try {
				String lambda = new LambdaToRDL().getDRLCommand(whenClause);
				FileUtils.writeStringToFile(new File(this.pathLambdaCompiledDRL), lambda);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getFilterThing() throws IOException {
		return this.filterThing;		
	}
	//filtra pelo nome da coisa profisoriamente
	public void setFilterThing(String filter)
	{
		this.filterThing = filter;
	}		
}