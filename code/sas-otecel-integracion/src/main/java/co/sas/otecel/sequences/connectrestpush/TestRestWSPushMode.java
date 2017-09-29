package co.sas.otecel.sequences.connectrestpush;

import org.apache.camel.builder.RouteBuilder;

public class TestRestWSPushMode  extends RouteBuilder  {

	@Override
	public void configure() throws Exception {
		//  from ("direct:start").routeId("Biwise345").bean(Biwise345Service.class,"status").to("log:hello");		
		from("jbi:service:http://foo.bar.org/MyService")
	    .streamCaching()
	    .to("jbi:service:http://foo.bar.org/MyOtherService");
		
		from("jetty:http://api.relay42.com/v1/site-1252/profiles/interactions/stream").to("log:hello");
//	from("netty4:tcp://api.relay42.com/v1/site-1252/profiles/interactions/stream?sync=true"
		//	 ).to("log:hello");
		//SSLSession session = exchange.getIn().getHeader(NettyConstants.NETTY_SSL_SESSION, SSLSession.class);
		//from("netty4:tcp://api.relay42.com/v1/site-1252/profiles/interactions/stream").to("log:hello");
		/*from("netty4:tcp://api.relay42.com/v1/site-1252/profiles/interactions/stream").process(new Processor() {
		    public void process(Exchange exchange) throws Exception {
		        String body = exchange.getIn().getBody(String.class);
		        exchange.getOut().setBody("Bye " + body);
		        // some condition which determines if we should close
		        if (close) {
		            exchange.getOut().setHeader(NettyConstants.NETTY_CLOSE_CHANNEL_WHEN_COMPLETE, true);
		        }
		    }
		}
	);*/
	}
	
}
