package co.sas.otecel.initializator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.sas.otecel.test.GreetingService;

@RestController
public class HomeController {

    @Autowired
    GreetingService greetingService;

    @RequestMapping("/greetingService")
    public String home() {
        return greetingService.greet();
    }
}