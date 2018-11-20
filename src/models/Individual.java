package models;

import java.util.List;


public class Individual implements Comparable<Individual>{
	private String name;
	private Classifier rootMethod;  
	private List<Classifier> classifiers;
	private Double accuracy;
	//method to return accuracy
	@Override
	public int compareTo(Individual individual) {
		if(this.accuracy<individual.accuracy) {
			return 1;
		}
		else {
			return -1;
		}
	}
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
	public List<Classifier> getClassifiers() {
		return classifiers;
	}
	public void setClassifiers(List<Classifier> classifiers) {
		this.classifiers = classifiers;
	}
	public Double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Double accuracy) {
		this.accuracy = accuracy;
	}
	@Override
	public String toString() {
		String classi="";
		for (Classifier classifier : classifiers) {
			classi += classifier.getNome() +"\n" ;
		}
		return "Name: "+this.name+"\n Accuracy: "+this.accuracy+"\n Root Method: "+this.rootMethod.getNome() +"\n Classifiers:" +classi;
	}

}
