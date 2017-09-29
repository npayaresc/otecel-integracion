package co.sas.otecel.sequences.aaggateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("sampleBean")
public class SampleBean {

    @Value("${greeting}")
    private String say;

    public String saySomething() {
        return say;
    }

}