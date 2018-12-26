package br.ufrn.imd.pbil.domain;

import java.util.ArrayList;
import java.util.Map;

public abstract class ClassifierFactory {
	
	public Map<String, ClassifierBuilder> builders;
	public ArrayList<String> classifierNames;
	
	public Classifier buildClassifierRandomly(String name) {
		return builders.get(name).randomBuild();
	}
	
	public Classifier buildClassifierByDefaul(String name) {
		return builders.get(name).defaultBuild();
	}

	public Map<String, ClassifierBuilder> getBuilders() {
		return builders;
	}

	public void setBuilders(Map<String, ClassifierBuilder> builders) {
		this.builders = builders;
	}

	public ArrayList<String> getClassifierNames() {
		return classifierNames;
	}

	public void setClassifierNames(ArrayList<String> classifierNames) {
		this.classifierNames = classifierNames;
	}
	
}
