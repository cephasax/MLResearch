package br.ufrn.imd.pbil.domain.baseclassifiers;

import java.util.Random;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Parameter;

public abstract class ClassifierBuilder {
	
	protected Classifier classifier;
	protected ClassifierPrototype prototype;	
	
	public ClassifierBuilder(ClassifierPrototype classifierPrototype) {
		this.prototype = classifierPrototype;
	}
	
	public abstract Classifier defautBuild();
	
	public abstract Classifier randomBuild();
	
	public abstract Classifier weightedDrawBuild();
	
	protected int getSizeOfPossibilities(Parameter p){
		return prototype.getParameters().get(p.getName()).getPossibilities().size();
	}
	
	protected String randomValueForParameter(Parameter p) {
		Random random = new Random();
		int randomizedInt = random.nextInt(getSizeOfPossibilities(p));
		String temp = (prototype.getParameters().get(p.getName()).getPossibilities().get(randomizedInt)).toString();
		return temp;
	}
}
