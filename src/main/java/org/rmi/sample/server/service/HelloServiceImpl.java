package org.rmi.sample.server.service;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class HelloServiceImpl implements HelloService {
	
	private Registry registry;
	
	public void setRegistry(Registry registry) {
		this.registry = registry;
	}
	
	public String sayHello(String name) throws RemoteException {
		return "Hello "+name;
	}
	
	public void stopService() throws RemoteException, Exception {
		System.out.println("Stopping the service...");
		
		if(registry != null) {
			new Thread(() -> {
				try {
					UnicastRemoteObject.unexportObject(registry, true);
					System.exit(0);
				} catch(Exception exception) {
					System.out.println("Error shutting down the system");
				}
			}).start();
		}
		
		System.out.println("Hello Service Stopped...");
	}
}