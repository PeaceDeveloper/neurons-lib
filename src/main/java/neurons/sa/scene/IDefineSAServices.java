package neurons.sa.scene;

public interface IDefineSAServices {
	SituationAware on(String pathToProject, Boolean isWriteToFile);
	IDefineSAServices getThis();
}