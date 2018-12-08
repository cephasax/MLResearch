package br.ufrn.imd.pbil.domain;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pbil.enums.ClassifierType;

public abstract class Classifier {

	protected ClassifierType classifierType;
	protected String name;
	protected List<Parameter> parameters;

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
		return this.name +": "+this.parameters.toString();
	}
	public void print() {
		System.out.println(name+":{");
		for (Parameter parameter : parameters) {
			parameter.print();
		}
		System.out.println("}");
	}
}
