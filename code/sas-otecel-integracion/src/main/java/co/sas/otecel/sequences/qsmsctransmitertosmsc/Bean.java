package co.sas.otecel.sequences.qsmsctransmitertosmsc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("myBean")
public class Bean {

    @Value("${greeting}")
    private String say;

    public String saySomething() {
        return say;
    }

}