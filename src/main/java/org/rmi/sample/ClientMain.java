package org.rmi.sample;
import org.rmi.sample.client.service.ServiceManagerImpl;

public class ClientMain {
	
	private static final String MAIN_CLASS = "org.rmi.sample.server.ServerMain";

	public static void main(String... args) throws Exception {
		ServiceManagerImpl serviceManager = new ServiceManagerImpl();
		
		serviceManager.start(MAIN_CLASS);
		serviceManager.service("Ramesh");
		serviceManager.stop();
	}
}