package neurons.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeoutException;
import com.labviros.is.Message;
import com.labviros.is.ServiceClient;
import com.labviros.is.ServiceProvider;

public interface IConnection extends Serializable {
	
	//cria se não existe um exchange do tipo topic como o do rabbitmq 
	void assertExchange(String name);
	
	//finaliza a conexão
	void stop() throws IOException, TimeoutException;
	
	//assina a um serviço para receber eventos do tipo Message
	ArrayBlockingQueue<Message> subscribe(String exchange, String topic) throws IOException;
	
	//remove a assinatura
	void unsubscribe(String exchange, String topic) throws IOException;
	
	//publica eventos
	void publish(String exchange, String topic, Message message) throws Exception;
	
	//publica eventos e registra serviço
	void publishServiceDelivery(String topic, Message message) throws Exception;
	
	//gera serviços para a plataforma
	<T> ServiceProvider<T> advertise(T impl) throws IOException;
	
	//retorna o cliente para consumir os serviços da plataforma
	ServiceClient client();
	
	AbstractPlatform Map();
}