package neurons.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import neurons.util.FileUtil;

public class Neurons {
	
	private String name;
	private String description;
	private String version;
	private String connectionUrl;
	private String datasourceUrl;
	private String defineSAServiceClass;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDefineSAServiceClass() {
		return defineSAServiceClass;
	}
	public void setDefineSAServiceClass(String defineSAServiceClass) {
		this.defineSAServiceClass = defineSAServiceClass;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDatasourceUrl() {
		return datasourceUrl;
	}
	public void setDatasourceUrl(String datasourceUrl) {
		this.datasourceUrl = datasourceUrl;
	}
	public String getConnectionUrl() {
		return connectionUrl;
	}
	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}
	
	public static Neurons newNeuronsConfig(String pathToDirectory) throws FileNotFoundException, IOException{
		Path pathToNeuronsxml = Paths.get(pathToDirectory + "/neurons.xml");	
		return (Neurons)FileUtil.deserializeXmlFile(pathToNeuronsxml.toString(), Neurons.class);
	}
}