package co.sas.otecel.initializator;

import javax.annotation.PostConstruct;
import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.sas.otecel.test.GreetingService;
import io.hawt.config.ConfigFacade;
import io.hawt.springboot.HawtPlugin;
import io.hawt.web.AuthenticationFilter;

//same as @Configuration @EnableAutoConfiguration @ComponentScan
@SpringBootApplication(scanBasePackages = { "co.sas.otecel.test","co.sas.otecel.sequences","co.sas.otecel.initializator" })
@RestController
public class SpringBootWebApplication {

	@Autowired
	private GreetingService greetingService;

	@Autowired
	private CamelContext camelContext;

	public SpringBootWebApplication() {
	}

	public static void main(String[] args) {
		System.setProperty(AuthenticationFilter.HAWTIO_AUTHENTICATION_ENABLED, "false");
		SpringApplication.run(SpringBootWebApplication.class, args);
	}

	@RequestMapping("/")
	String home() {
		return "Otecel Server Integrator is Running ! ".concat(greetingService.greet());
	}

	/**
	 * Loading an example plugin.
	 */
	@Bean
	public HawtPlugin samplePlugin() {
		return new HawtPlugin("sample-plugin", "/hawtio/plugins", "",
				new String[] {"sample-plugin/js/sample-plugin.js" });
	}

	/**
	 * Set things up to be in offline mode.
	 */
	@Bean
	public ConfigFacade configFacade() {
		System.setProperty("hawtio.offline", "true");
		return ConfigFacade.getSingleton();
	}

	/*
	 * Camel boot auto configuration deberia inicar todas las rutas pero no lo esta
	 * realizando.
	 * 
	 * Por ese motivo se inicia desde aqui mientras se soluciona.
	 * 
	 */
	@PostConstruct
	public void postConstruct() {
		try {
			//co.sas.otecel.sequences.qsmsctransmitertosmsc.Router qsmsctransmitertosmscrouter = new co.sas.otecel.sequences.qsmsctransmitertosmsc.Router();
			//camelContext.addRoutes(qsmsctransmitertosmscrouter);

			/*
			 * Router seqQEspInboundToEsp = new Router();
			 * camelContext.addRoutes(seqQEspInboundToEsp);
			 * 
			 * Router seqEspToQSmscInbound = new Router();
			 * camelContext.addRoutes(seqEspToQSmscInbound);
			 * 
			 * Router seqEspToRtdmInbound = new Router();
			 * camelContext.addRoutes(seqEspToRtdmInbound);
			 * 
			 * Router seqQRtdmInboundToRtdm = new Router();
			 * camelContext.addRoutes(seqQRtdmInboundToRtdm);
			 * 
			 * Router seqRtdmToQTiaxaInbound = new Router();
			 * camelContext.addRoutes(seqRtdmToQTiaxaInbound);
			 * 
			 * Router seqQSmscInboundToSmsc = new Router();
			 * camelContext.addRoutes(seqQSmscInboundToSmsc);
			 * 
			 * Router seqQTiaxaInboundToTiaxa = new Router();
			 * camelContext.addRoutes(seqQTiaxaInboundToTiaxa);
			 */
			
			co.sas.otecel.sequences.integratorreceiver.Router integratorreceiver = new co.sas.otecel.sequences.integratorreceiver.Router();
			camelContext.addRoutes(integratorreceiver);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}