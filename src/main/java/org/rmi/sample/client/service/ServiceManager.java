package org.rmi.sample.client.service;
public interface ServiceManager {
	
	void start(String mainClassName) throws Exception;
	
	void stop() throws Exception;
	
	void service(String callString) throws Exception;
	
	void reStart() throws Exception;

}