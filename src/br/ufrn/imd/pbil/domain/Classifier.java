package br.ufrn.imd.pbil.domain;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.ufrn.imd.pbil.enums.ClassifierType;

public abstract class Classifier {

	protected ClassifierType classifierType;
	protected String name;
	protected List<Parameter> parameters;
	public List<Classifier> getClassifiers(){
		return new ArrayList<Classifier>();
	}
	
	public Classifier() {
		this.name = new String();
		this.parameters = new ArrayList<Parameter>();
	}
	
	public void addParameter(Parameter parameter) {
		this.parameters.add(parameter);
	}

	public ClassifierType getClassifierType() {
		return classifierType;
	}

	public void setClassifierType(ClassifierType classifierType) {
		this.classifierType = classifierType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	@Override
	public String toString() {
		Gson gson = new Gson();
		String a = gson.toJson(this, Classifier.class);
		jsonFormatter(a);
		return a;
	}
	
	public void print() {
		Gson gson = new Gson();
		String a = gson.toJson(this, Classifier.class);
		a= jsonFormatter(a);
		System.out.println(a);
	}
	
	protected static String jsonFormatter(String text) {
	    JsonParser parser = new JsonParser();
	    JsonObject json = parser.parse(text).getAsJsonObject();
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    String prettyJson = gson.toJson(json);			
	    return prettyJson;
	}
	
}
