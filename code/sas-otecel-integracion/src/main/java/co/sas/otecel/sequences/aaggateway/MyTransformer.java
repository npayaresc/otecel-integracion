package co.sas.otecel.sequences.aaggateway;

public class MyTransformer {
	
	public String TransformerContext(String body) {
		String upperCaseContext = body.toUpperCase();
		return upperCaseContext;
	}
}
