package br.ufrn.imd.pbil.domain.bc.builders;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.ClassifierBuilder;
import br.ufrn.imd.pbil.domain.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.bc.BayesNet;
import br.ufrn.imd.pbil.enums.ClassifierType;
import br.ufrn.imd.pbil.enums.ParameterType;

public class BayesNetBuilder extends ClassifierBuilder{

	public BayesNetBuilder(ClassifierPrototype classifierPrototype) {
		super(classifierPrototype);
	}

	@Override
	public Classifier defaultBuild() {
		classifier =new BayesNet();
		classifier.setName("BayesNet");
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter q = new Parameter("Q",ParameterType.STRING);
		q.setValue("K2");
		classifier.addParameter(q);
		
		Parameter d = new Parameter("D", ParameterType.BOOLEAN);
		d.setValue("false");
		classifier.addParameter(d);
		
		return classifier;
	}

	@Override
	public Classifier randomBuild() {
		classifier = new BayesNet();
		classifier.setName("BayesNet");
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter q = new Parameter("Q", ParameterType.STRING);
		q.setValue(randomValueForParameter(q));
		classifier.addParameter(q);
		
		Parameter d = new Parameter("D", ParameterType.BOOLEAN);
		d.setValue(randomValueForParameter(d));
		classifier.addParameter(d);
		
		return classifier;
	}
}
