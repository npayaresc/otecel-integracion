package com.sas.servers;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sas.smppserver.SasSmppServer;
import org.springframework.stereotype.Service;

@Service
public class Biwise345Service  {
	
	private static final Logger logger = LoggerFactory.getLogger(SasSmppServer.class);
	
	private static SasSmppServer server;

	public Biwise345Service() {
		logger.info("loading Biwise345Service.....");
	}
	
	@PostConstruct
	public void start() throws Exception {
		if (server==null) {
			logger.info("loading properties.....");
			server = new  SasSmppServer("Biwise345.properties");	
			server.start();
			logger.info("loaded properties.....");
		}
	}	
	
	@PreDestroy
	public void destroy() throws Exception {
		if (server!=null) {
			server.stop();
		}
	}
	
	public void status() {
		logger.info("**********************************************");
		logger.info(server==null?"Error el servidor no se pudo crear Biwise345Service : ":server.toString());
		logger.info("**********************************************");
	}
	

}
