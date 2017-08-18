package com.sas.agent.com.sas.agent.routes;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.language.Bean;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class AgentToTargetRoutes extends RouteBuilder {

    @Override
    public void configure() {
        restConfiguration()
                //.contextPath("/rest")
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "CI360 Capture Event API")
                .apiProperty("api.version", "1.0")
                .apiProperty("cors", "true")
                .apiContextRouteId("doc-api")
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                //.port(59191)
                .enableCORS(true);

        rest("/events").description("CI360 Capture Event service")
                .consumes("application/json").produces("application/json")
                .post("/all").description("capturing ci360 json object")
                //.responseMessage().code(200).message("Event successfully uploaded").endResponseMessage()
                .to("direct:route1");

        rest("/say")
                .get("/hello").to("direct:hello")
                .get("/bye").consumes("application/json").to("direct:bye")
                .post("/bye").to("mock:update");

        from("direct:hello")
                .transform().constant("Hello World");
        from("direct:bye")
                .transform().constant("Bye World");
        from("direct:route1").to("mock:update");
        from("direct:route1").log("CI Event message: ${body}").transform(method("transformer", "transformJson(${body})")).log("CI Map: ${body}").to("dfESP://192.168.99.100:55555/CaptureDigitalBehavior4/digital_stream/digital_event_src?producerDefaultOpcode=eo_INSERT&bodyType=event").routeId("Converting CI360 Events");

    }


}
