package br.ufrn.imd.pbilautoens;

public abstract class ClassifierBuilder {
	
	protected Classifier classifier;
	protected ClassifierPrototype prototype;
	protected PbilRandom pbilRandom;
	
	public ClassifierBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		this.prototype = classifierPrototype;
		this.pbilRandom = pbilRandom;
	}
	
	public abstract Classifier defaultBuild();
	
	public abstract Classifier randomBuild();
	
	public int getSizeOfPossibilities(ParameterPrototype p){
		return prototype.getParameters().get(p.getName()).getPossibilities().size();
	}
	
	public String randomValueForParameter(Parameter p) {
		ParameterPrototype pP= prototype.parameters.get(p.getName());
		int randomizedInt = pbilRandom.nextInt(getSizeOfPossibilities(pP));
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
