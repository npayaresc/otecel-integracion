package co.sas.otecel.sequences.qsmsctransmitertosmsc;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Router  extends RouteBuilder  {

	private Logger logger = LoggerFactory.getLogger(RouteBuilder.class);
	
	@Override
    public void configure() throws Exception {
		
		String log = "log:org.smsrouter?level=INFO";
		
        //from("direct:start")
                //.process(new SmppAddressingProcessor())
         //       .to(log)
         //       .to("smpp://smppclient@localhost:2776?password=password&enquireLinkTimer=3000&transactionTimer=5000&systemType=producer")
         //       .to(log)
         //       ;
	}   

}
 