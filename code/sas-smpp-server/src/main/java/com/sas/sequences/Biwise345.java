package com.sas.sequences;

import org.apache.camel.builder.RouteBuilder;

import com.sas.servers.Biwise345Service;

public class Biwise345 extends RouteBuilder {

	public Biwise345(){
	}
	
	@Override
	public void configure() throws Exception {
		from ("direct:start").routeId("Biwise345").bean(Biwise345Service.class,"status").to("log:hello");
	}
}
