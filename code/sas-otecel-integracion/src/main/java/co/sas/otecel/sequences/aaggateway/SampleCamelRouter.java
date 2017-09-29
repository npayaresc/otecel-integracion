package co.sas.otecel.sequences.aaggateway;


import org.springframework.stereotype.Component;
import org.apache.camel.builder.RouteBuilder;

@Component
public class SampleCamelRouter extends RouteBuilder {

	@Override
    public void configure() throws Exception {
        //from("timer:hello?period={{timer.period}}")
		//        .transform(method("myBean", "saySomething"))
		//        .to("stream:out");
	}    
}
