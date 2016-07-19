package org.rmi.sample.client.service;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.rmi.sample.server.service.HelloService;

public class ServiceManagerImpl implements ServiceManager {

	public static final String JAVA_COMMAND = "java";
	
	private HelloService helloService;
	
	private String mainClassName;
	
	public void service(String callString) throws Exception {
		System.out.println(helloService.sayHello(callString));
	}
	
	public void start(String mainClassName) throws Exception {
		this.mainClassName = mainClassName;
		String classpath = Arrays.stream(((URLClassLoader) Thread.currentThread().getContextClassLoader()).getURLs())
				.map(URL::getFile).collect(Collectors.joining(File.pathSeparator));
		System.out.println("Starting the server with the main class: " + mainClassName);
		List<String> environment = Arrays.asList(JAVA_COMMAND, "-cp", classpath, mainClassName);

		ProcessBuilder processBuilder = new ProcessBuilder(environment);
		
		processBuilder.inheritIO();
		//processBuilder.redirectOutput(new File("Server.log"));

		Process process = processBuilder.start();
		
		BufferedReader buffer = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
        String streamContent = buffer.lines().collect(Collectors.joining("\n"));
        
		System.out.println(streamContent);
		
		Registry registry = LocateRegistry.getRegistry(HelloService.PORT);
		
		this.helloService = (HelloService)registry.lookup(HelloService.SERVICE_NAME);
	}
	
	public void stop() throws Exception {
		System.out.println("Stopping the server...");	
		
		helloService.stopService();
		
		System.out.println("Server Stopped");
	}
	
	public void reStart() throws Exception {
		stop();
		start(mainClassName);
	}
	
}