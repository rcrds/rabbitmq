package co.com.ceiba.charlas.rabbitmq.publishsubscribe;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public final class ConfigurationBroker {
	
	
	public static final String EXCHANGE = "logs";
	private static final String HOST = "localhost";
	private static ConfigurationBroker instance;
	private ConnectionFactory factory = new ConnectionFactory();
	private Connection conexion;
	private Channel canal;
		
	private ConfigurationBroker(String host) throws IOException, TimeoutException {
		createConnections(host);
	}

	private void createConnections(String host) throws IOException, TimeoutException {
		factory.setHost( host );
		conexion = factory.newConnection();
		canal = conexion.createChannel();
		canal.exchangeDeclare(EXCHANGE, "fanout");
		
	}
	
	private ConfigurationBroker() throws IOException, TimeoutException {
		createConnections(HOST);
	}

	public static synchronized ConfigurationBroker getIntance(String host) throws IOException, TimeoutException {
		if( instance == null ){
			instance = new ConfigurationBroker( host );
		}
		
		return instance;
	}
	
	public static synchronized ConfigurationBroker getIntance() throws IOException, TimeoutException{
		if( instance == null ){
			instance = new ConfigurationBroker();
		}
		return instance;
	}
	
	public void closeConnections() throws IOException, TimeoutException{
		canal.close();
		conexion.close();
	}
	
	public Channel getCanal() {
		return canal;
	}
}
