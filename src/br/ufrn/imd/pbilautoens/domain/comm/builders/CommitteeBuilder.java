package br.ufrn.imd.pbilautoens.domain.comm.builders;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pbilautoens.Classifier;
import br.ufrn.imd.pbilautoens.ClassifierBuilder;
import br.ufrn.imd.pbilautoens.ClassifierPrototype;
import br.ufrn.imd.pbilautoens.Committee;
import br.ufrn.imd.pbilautoens.PbilRandom;
import br.ufrn.imd.pbilautoens.domain.bc.BaseClassifierFactory;
import br.ufrn.imd.pbilautoens.exception.InvalidParameterTypeException;

public abstract class CommitteeBuilder extends ClassifierBuilder{
	
	public Committee committee;
	
	public CommitteeBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}
	
	// build a list of classifier with size equals to the number of classifiers each committee need
	protected List<Classifier> buildClassifiers(int numberOfClassifiers) throws InvalidParameterTypeException {
		
		List<Classifier> classifiers = new ArrayList<Classifier>();
		BaseClassifierFactory factory = new BaseClassifierFactory(pbilRandom);
		
		for (int i = 0; i < numberOfClassifiers; i++) {	
			int indexOfClassifier = pbilRandom.nextInt(9);
			String nameOfClassifier = factory.getClassifierNames().get(indexOfClassifier);
			
			Classifier solucao = factory.buildClassifierRandomly(nameOfClassifier);
			classifiers.add(solucao);
		}
		return classifiers;
	}
	
}
