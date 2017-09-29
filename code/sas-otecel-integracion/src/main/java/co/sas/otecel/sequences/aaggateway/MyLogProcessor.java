package co.sas.otecel.sequences.aaggateway;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MyLogProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		System.out.println("New process os string : " + exchange.getIn().getBody(String.class) );
	}

}
