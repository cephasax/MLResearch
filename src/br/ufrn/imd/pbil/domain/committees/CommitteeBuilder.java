package br.ufrn.imd.pbil.domain.committees;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.baseclassifiers.ClassifierFactory;
import br.ufrn.imd.pbil.domain.baseclassifiers.ParameterPrototype;
import br.ufrn.imd.pbil.enums.ConfigurationType;
import br.ufrn.imd.pbil.enums.BaseClassifierType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public abstract class CommitteeBuilder {
	
	public CommitteeBuilder(CommitteePrototype committeePrototype) {
		this.prototype = committeePrototype;
	}
	
	Committee committee;
	
	CommitteePrototype prototype;
	
	public abstract Committee defaultBuild();
	
	public abstract Committee randomBuild();
	
	public abstract Committee weightedBuild();

	protected int getSizeOfPossibilities(ParameterPrototype p){
		return prototype.getParameters().get(p.getName()).getPossibilities().size();
	}
	
	// build a list of classifier with size equals to the number of classifiers each committee need
	protected List<Classifier> buildClassifiers(int numberOfClassifiers) throws InvalidParameterTypeException {
		List<Classifier> classifiers = new ArrayList<Classifier>();
		Random random =new Random();
		for (int i =0; i<numberOfClassifiers; i++) {	
			int indexOfConfig = random.nextInt(2);
			int indexOfClassifier = random.nextInt(9);
			BaseClassifierType type = BaseClassifierType.values()[indexOfClassifier];
			ConfigurationType config  =ConfigurationType.values()[indexOfConfig];	
			Classifier solucao = ClassifierFactory.buildClassifier(type, config);
			classifiers.add(solucao);
		}
		return classifiers;
	}
	
	protected String randomValueForParameter(Parameter p) {
		Random random = new Random();
		ParameterPrototype pP= prototype.parameters.get(p.getName());
		int randomizedInt = random.nextInt(getSizeOfPossibilities(pP));
		String temp = (prototype.getParameters().get(p.getName()).getPossibilities().get(randomizedInt)).toString();
		return temp;
	}
}
