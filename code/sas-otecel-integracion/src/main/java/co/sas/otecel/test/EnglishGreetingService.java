package co.sas.otecel.test;

import java.text.DateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class EnglishGreetingService implements GreetingService {

	private Date fecha;
	private DateFormat format;
	
	public EnglishGreetingService() {
		 fecha = new Date();
		 format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
	}
	
    @Override
    public String greet() {
        return "Hello World : " + format.format(fecha) ;
    }
}
