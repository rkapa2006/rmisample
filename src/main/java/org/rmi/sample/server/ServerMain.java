package org.rmi.sample.server;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.rmi.sample.server.service.HelloService;
import org.rmi.sample.server.service.HelloServiceImpl;

import java.rmi.Remote;

public class ServerMain {

	public static void main(String... args) throws Exception {
		
		System.out.println("Starting Echo Server...");
		
		Registry registry = startRegistry(HelloService.PORT);
		
		HelloService service = new HelloServiceImpl();
		
		exportService(service);
		System.out.println("Binding Hello Service with name: "+HelloService.SERVICE_NAME);
		
		registry.bind(HelloService.SERVICE_NAME, service);
		
		((HelloServiceImpl)service).setRegistry(registry);
		
		addShutdownHandler(registry);
		
		System.out.println("Started Echo Server Successfully..");
		System.out.println("..........................");
	}
	
	private static void exportService(Remote remote) throws Exception {
		System.out.println("Exporting remote server: "+remote.getClass().getName());
		UnicastRemoteObject.exportObject(remote, 0);
		
	} 
	
	private static void addShutdownHandler(Registry registry) throws Exception {
		System.out.println("Adding shutdown hook");
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("Shutting down the EchoServer...");
			try {
				System.out.println("Exiting the Server Process...");
				System.out.println(".............................");
				System.out.println("##############################");
				//UnicastRemoteObject.unexportObject(registry, true);
			} catch(Exception exception) {
				System.out.println("Error stopping registry");
				exception.printStackTrace();
			}
		}, "ShutdownHandlerThread"));
	}
	
	private static Registry  startRegistry(int PORT) throws Exception {
		
		System.out.println("Starting Registry Service...");
		
		return LocateRegistry.createRegistry(PORT);
	}
}