package br.ufrn.imd.pbil.domain;

import java.util.List;

public abstract class ClassifierProbability {

	private String name;
	private int value;
	private List<ClassifierProbability> subClassifiers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public List<ClassifierProbability> getSubClassifiers() {
		return subClassifiers;
	}

	public void setSubClassifiers(List<ClassifierProbability> subClassifiers) {
		this.subClassifiers = subClassifiers;
	}

}
