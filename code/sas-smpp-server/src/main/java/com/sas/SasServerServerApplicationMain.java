package com.sas;

import javax.annotation.PostConstruct;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sas.sequences.Biwise345;

import io.hawt.config.ConfigFacade;
import io.hawt.springboot.HawtPlugin;
import io.hawt.web.AuthenticationFilter;

@SpringBootApplication(scanBasePackages = { "com.sas.sequences","com.sas.servers","com.sas.smppserver" })
@RestController
public class SasServerServerApplicationMain {
	
	@Autowired
	private CamelContext camelContext;
	
	public SasServerServerApplicationMain() {
	}
	
	public static void main(String[] args) {
		System.setProperty(AuthenticationFilter.HAWTIO_AUTHENTICATION_ENABLED, "false");
		SpringApplication.run(SasServerServerApplicationMain.class, args);
	}

	/**
	 * Loading an example plugin.
	 */
	@Bean
	public HawtPlugin samplePlugin() {
		return new HawtPlugin("sample-plugin", "/hawtio/plugins", "",new String[] {"sample-plugin/js/sample-plugin.js" });
	}

	/**
	 * Set things up to be in offline mode.
	 */
	@Bean
	public ConfigFacade configFacade() {
		System.setProperty("hawtio.offline", "true");
		return ConfigFacade.getSingleton();
	}

	
	@RequestMapping("/report")
	public String report() {
		return "Otecel Smpp Server is Running !";
	}
	
	@PostConstruct
	public void postConstruct() {
		try {
			Biwise345 biwise345 = new Biwise345();
			camelContext.addRoutes(biwise345);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
