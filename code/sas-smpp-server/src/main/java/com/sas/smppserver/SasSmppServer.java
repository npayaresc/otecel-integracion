package com.sas.smppserver;

	/*
	 * #%L
	 * ch-smpp
	 * %%
	 * Copyright (C) 2009 - 2015 Cloudhopper by Twitter
	 * %%
	 * Licensed under the Apache License, Version 2.0 (the "License");
	 * you may not use this file except in compliance with the License.
	 * You may obtain a copy of the License at
	 * 
	 *      http://www.apache.org/licenses/LICENSE-2.0
	 * 
	 * Unless required by applicable law or agreed to in writing, software
	 * distributed under the License is distributed on an "AS IS" BASIS,
	 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	 * See the License for the specific language governing permissions and
	 * limitations under the License.
	 * #L%
	 */

import com.cloudhopper.smpp.SmppServerConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppServer;
import com.cloudhopper.smpp.type.SmppChannelException;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author joelauer (twitter: @jjlauer or
 *         <a href="http://twitter.com/jjlauer" target=
 *         window>http://twitter.com/jjlauer</a>)
 */
public class SasSmppServer {
	
	private static final Logger logger = LoggerFactory.getLogger(SasSmppServer.class);
	
	private String filename;

	private String serverName;
	
	private DefaultSmppServer smppServer;
	
	public SasSmppServer(String filename) {
		this.filename=filename;
	}
	
	@PostConstruct
	public void init() {
		try {
			//
			// setup 3 things required for a server
			//

			// for monitoring thread use, it's preferable to create your own instance
			// of an executor and cast it to a ThreadPoolExecutor from
			// Executors.newCachedThreadPool()
			// this permits exposing things like executor.getActiveCount() via JMX possible
			// no point renaming the threads in a factory since underlying Netty
			// framework does not easily allow you to customize your thread names
			ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

			// to enable automatic expiration of requests, a second scheduled executor
			// is required which is what a monitor task will be executed with - this
			// is probably a thread pool that can be shared with between all client
			// bootstraps
			ScheduledThreadPoolExecutor monitorExecutor = 
					(ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1,
							new ThreadFactory() {
								private AtomicInteger sequence = new AtomicInteger(0);
								@Override
								public Thread newThread(Runnable r) {
									Thread t = new Thread(r);
									t.setName("SmppServerSessionWindowMonitorPool-" + sequence.getAndIncrement());
									return t;
								}
							}
					);

			
			//Load File Properties
			Properties prop = new Properties();
			prop.load(getClass().getClassLoader().getResourceAsStream(filename));
			
			SmppServerConfiguration configuration = new SmppServerConfiguration();
			configuration.setPort(Integer.valueOf(prop.getProperty("port")));
			configuration.setMaxConnectionSize(Integer.valueOf(prop.getProperty("maxConnectionSize")));
			configuration.setNonBlockingSocketsEnabled(Boolean.valueOf(prop.getProperty("nonBlockingSocketsEnabled")));
			configuration.setDefaultRequestExpiryTimeout(Integer.valueOf(prop.getProperty("maxConnectionSize")));
			configuration.setDefaultWindowMonitorInterval(Integer.valueOf(prop.getProperty("maxConnectionSize")));
			configuration.setDefaultWindowSize(Integer.valueOf(prop.getProperty("maxConnectionSize")));
			configuration.setDefaultWindowWaitTimeout(Integer.valueOf(prop.getProperty("defaultWindowWaitTimeout")));
			configuration.setDefaultSessionCountersEnabled(Boolean.valueOf(prop.getProperty("defaultSessionCountersEnabled")));
			configuration.setJmxEnabled(Boolean.valueOf(prop.getProperty("defaultSessionCountersEnabled")));
			serverName=prop.getProperty("serverName");
			// create a server, start it up
			smppServer = new DefaultSmppServer(configuration, new SasSmppServerHandler(), executor, monitorExecutor);
			
		} catch (Exception ex) {
			logger.info(ex.getMessage());
		}
	}//
	

	public void start() throws SmppChannelException {
		logger.info("Starting SMPP server:".concat(serverName==null?"Server is Null":serverName).concat("..."));
		smppServer.start();
		logger.info("SMPP server : ".concat(serverName==null?"Server is Null":serverName).concat(" started"));
	}

	public void stop() throws SmppChannelException {
		logger.info("Stopping SMPP server...");
		smppServer.stop();
		logger.info("SMPP server stopped");
		logger.info("Server counters: {}", smppServer.getCounters());
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

}