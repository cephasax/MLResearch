package br.ufrn.imd.pbil.domain.builders;

import java.util.Random;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.prototypes.ClassifierPrototype;

public abstract class ClassifierBuilder {
	
	protected Classifier classifier;
	protected ClassifierPrototype prototype;	
	
	public abstract Classifier defautBuild();
	
	public abstract Classifier randomBuild();
	
	public abstract Classifier wheitedDrawBuild();
	
	protected int getSizeOfPossibilities(Parameter p){
		return prototype.getParameters().get(p.getName()).getPossibilities().size();
	}
	
	protected String randomValueForParameter(Parameter p) {
		Random random = new Random();
		int randomizedInt = random.nextInt(getSizeOfPossibilities(p));
		String temp = (String)(prototype.getParameters().get(p.getName()).getPossibilities().get(randomizedInt));
		return temp;
	}
}
