package br.ufrn.imd.pbil.domain;

import java.util.List;

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

	@Override
	public String toString() {
		String classi = "";
		for (Classifier classifier : classifiers) {
			classi += classifier.getName() + "\n";
		}
		return "Name: " + this.name + "\n Accuracy: " + this.accuracy + "\n Root Method: " + this.rootMethod.getName()
				+ "\n Classifiers:" + classi;
	}

}
