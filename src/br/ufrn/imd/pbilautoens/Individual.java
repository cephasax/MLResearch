package br.ufrn.imd.pbilautoens;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Individual{
	
	private String name;
	private Classifier rootMethod;
	private Double accuracy;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Classifier getRootMethod() {
		return rootMethod;
	}

	public void setRootMethod(Classifier rootMethod) {
		this.rootMethod = rootMethod;
	}

	public Double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(Double accuracy) {
		this.accuracy = accuracy;
	}
	
	public String print(){
		Gson gson = new Gson();
		String a = gson.toJson(this, Individual.class);
		a= jsonFormatter(a);
		System.out.println(a);
		return a;
	}
	
	protected static String jsonFormatter(String text) {
	    JsonParser parser = new JsonParser();
	    JsonObject json = parser.parse(text).getAsJsonObject();
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    String prettyJson = gson.toJson(json);			
	    return prettyJson;
	}
}