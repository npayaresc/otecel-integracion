package co.sas.otecel.sequences.aaggateway;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SeqSasSmscConnector extends RouteBuilder {
	
	@Override
	public void configure() throws Exception {
		//from("smpp://smppclient1@localhost:2775?password=password&enquireLinkTimer=3000&transactionTimer=5000&systemType=consumer")
		//.to("bean:foo");
	}
}
