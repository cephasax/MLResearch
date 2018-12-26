package br.ufrn.imd.pbil.domain;

import java.util.Random;

public abstract class ClassifierBuilder {
	
	protected Classifier classifier;
	protected ClassifierPrototype prototype;	
	
	public ClassifierBuilder(ClassifierPrototype classifierPrototype) {
		this.prototype = classifierPrototype;
	}
	
	public abstract Classifier defaultBuild();
	
	public abstract Classifier randomBuild();
	
	public int getSizeOfPossibilities(ParameterPrototype p){
		return prototype.getParameters().get(p.getName()).getPossibilities().size();
	}
	
	public String randomValueForParameter(Parameter p) {
		Random random = new Random();
		ParameterPrototype pP= prototype.parameters.get(p.getName());
		int randomizedInt = random.nextInt(getSizeOfPossibilities(pP));
		String temp = (prototype.getParameters().get(p.getName()).getPossibilities().get(randomizedInt)).toString();
		return temp;
	}

	public Classifier getClassifier() {
		return classifier;
	}

	public void setClassifier(Classifier classifier) {
		this.classifier = classifier;
	}

	public ClassifierPrototype getPrototype() {
		return prototype;
	}

	public void setPrototype(ClassifierPrototype prototype) {
		this.prototype = prototype;
	}
}
