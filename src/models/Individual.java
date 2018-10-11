package models;

import java.util.List;


public class Individual implements Comparable<Individual>{
	public String name;
	public Classifier rootMethod;  
	public List<Classifier> classifiers;
	public Double accuracy;
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
	@Override
	public String toString() {
		String classi="";
		for (Classifier classifier : classifiers) {
			classi += classifier.nome +"\n" ;
		}
		return "Name: "+this.name+"\n Accuracy: "+this.accuracy+"\n Root Method: "+this.rootMethod.nome +"\n Classifiers:" +classi;
	}

}
