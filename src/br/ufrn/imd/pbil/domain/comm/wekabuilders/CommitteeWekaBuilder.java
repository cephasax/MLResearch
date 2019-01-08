package br.ufrn.imd.pbil.domain.comm.wekabuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.ClassifierBuilder;
import br.ufrn.imd.pbil.domain.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.domain.bc.BaseClassifierFactory;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public abstract class CommitteeWekaBuilder extends ClassifierBuilder{
	
	public Committee committee;
	
	public CommitteeWekaBuilder(ClassifierPrototype classifierPrototype) {
		super(classifierPrototype);
	}
	
	// build a list of classifier with size equals to the number of classifiers each committee need
	protected List<Classifier> buildClassifiers(int numberOfClassifiers) throws InvalidParameterTypeException {
		
		List<Classifier> classifiers = new ArrayList<Classifier>();
		Random random =new Random();
		BaseClassifierFactory factory = new BaseClassifierFactory();
		
		for (int i = 0; i < numberOfClassifiers; i++) {	
			int indexOfClassifier = random.nextInt(9);
			String nameOfClassifier = factory.getClassifierNames().get(indexOfClassifier);
			
			Classifier solucao = factory.buildClassifierRandomly(nameOfClassifier);
			classifiers.add(solucao);
		}
		return classifiers;
	}
	
}
