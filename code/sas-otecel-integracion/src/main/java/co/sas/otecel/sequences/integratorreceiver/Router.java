package co.sas.otecel.sequences.integratorreceiver;

import org.apache.camel.builder.RouteBuilder;

import co.sas.otecel.sequences.aaggateway.MyLogProcessor;
import co.sas.otecel.sequences.aaggateway.MyTransformer;

public class Router extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from ("file://c://tmp//input?move=./done")
		.process(new MyLogProcessor())
		.bean(new MyTransformer(),"TransformerContext")
		.to("file://c://tmp/output");		
	}

}
