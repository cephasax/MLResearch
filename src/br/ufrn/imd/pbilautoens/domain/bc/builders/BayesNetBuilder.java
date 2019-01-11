package br.ufrn.imd.pbilautoens.domain.bc.builders;

import br.ufrn.imd.pbilautoens.Classifier;
import br.ufrn.imd.pbilautoens.ClassifierBuilder;
import br.ufrn.imd.pbilautoens.ClassifierPrototype;
import br.ufrn.imd.pbilautoens.Parameter;
import br.ufrn.imd.pbilautoens.PbilRandom;
import br.ufrn.imd.pbilautoens.domain.bc.BayesNet;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;
import br.ufrn.imd.pbilautoens.enums.ParameterType;

public class BayesNetBuilder extends ClassifierBuilder{

	public BayesNetBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}

	@Override
	public Classifier defaultBuild() {
		classifier =new BayesNet();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		
		Parameter d = new Parameter("D", ParameterType.BOOLEAN);
		d.setValue("false");
		classifier.addParameter(d);

		Parameter q = new Parameter("Q",ParameterType.STRING);
		q.setValue("weka.classifiers.bayes.net.search.local.K2");
		classifier.addParameter(q);
		
		
		return classifier;
	}

	@Override
	public Classifier randomBuild() {
		classifier = new BayesNet();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		
		Parameter d = new Parameter("D", ParameterType.BOOLEAN);
		d.setValue(randomValueForParameter(d));
		classifier.addParameter(d);

		Parameter q = new Parameter("Q", ParameterType.STRING);
		q.setValue(randomValueForParameter(q));
		classifier.addParameter(q);
		
		
		return classifier;
	}
}
