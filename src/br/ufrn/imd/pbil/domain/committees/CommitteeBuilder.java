package br.ufrn.imd.pbil.domain.committees;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.baseclassifiers.ClassifierBuilder;
import br.ufrn.imd.pbil.domain.baseclassifiers.ClassifierFactory;
import br.ufrn.imd.pbil.domain.baseclassifiers.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.baseclassifiers.ParameterPrototype;

import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public abstract class CommitteeBuilder extends ClassifierBuilder{
	
	
	public CommitteeBuilder(ClassifierPrototype classifierPrototype) {
		// TODO Auto-generated constructor stub
		super(classifierPrototype);
		
	}

	Committee committee;


	protected int getSizeOfPossibilities(ParameterPrototype p){
		return prototype.getParameters().get(p.getName()).getPossibilities().size();
	}
	
	// build a list of classifier with size equals to the number of classifiers each committee need
	protected List<Classifier> buildClassifiers(int numberOfClassifiers) throws InvalidParameterTypeException {
		List<Classifier> classifiers = new ArrayList<Classifier>();
		Random random =new Random();
		ClassifierFactory factory = new ClassifierFactory();
		for (int i =0; i<numberOfClassifiers; i++) {	
			int indexOfClassifier = random.nextInt(9);
			Classifier solucao = factory.buildClassifier(indexOfClassifier, 1);
			classifiers.add(solucao);
		}
		return classifiers;
	}
	
}
