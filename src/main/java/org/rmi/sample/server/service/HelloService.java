package org.rmi.sample.server.service;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloService extends Remote {
	
	public static final String SERVICE_NAME = "HelloService";
	
	public static final int PORT = 1080;
	
	public String sayHello(String name) throws RemoteException;
	
	public void stopService() throws RemoteException, Exception;
}