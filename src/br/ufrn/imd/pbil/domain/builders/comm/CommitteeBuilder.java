package br.ufrn.imd.pbil.domain.builders.comm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.ufrn.imd.pbil.annotations.ToFix;
import br.ufrn.imd.pbil.domain.BaseClassifierFactory;
import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.ClassifierBuilder;
import br.ufrn.imd.pbil.domain.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public abstract class CommitteeBuilder extends ClassifierBuilder{
	
	public Committee committee;
	
	public CommitteeBuilder(ClassifierPrototype classifierPrototype) {
		// TODO Auto-generated constructor stub
		super(classifierPrototype);
		
	}
	
	@ToFix
	// build a list of classifier with size equals to the number of classifiers each committee need
	protected List<Classifier> buildClassifiers(int numberOfClassifiers) throws InvalidParameterTypeException {
		List<Classifier> classifiers = new ArrayList<Classifier>();
		Random random =new Random();
		BaseClassifierFactory factory = new BaseClassifierFactory();
		for (int i =0; i<numberOfClassifiers; i++) {	
			int indexOfClassifier = random.nextInt(9);
			Classifier solucao = factory.buildClassifierRandomly(classifiers.get(indexOfClassifier).getName());
			classifiers.add(solucao);
		}
		return classifiers;
	}
	
}
