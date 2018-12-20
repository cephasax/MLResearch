package br.ufrn.imd.pbil.domain;


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
}