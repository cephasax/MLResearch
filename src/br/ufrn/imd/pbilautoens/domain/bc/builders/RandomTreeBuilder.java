package br.ufrn.imd.pbilautoens.domain.bc.builders;

import br.ufrn.imd.pbilautoens.Classifier;
import br.ufrn.imd.pbilautoens.ClassifierBuilder;
import br.ufrn.imd.pbilautoens.ClassifierPrototype;
import br.ufrn.imd.pbilautoens.Parameter;
import br.ufrn.imd.pbilautoens.PbilRandom;
import br.ufrn.imd.pbilautoens.domain.bc.RandomTree;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;
import br.ufrn.imd.pbilautoens.enums.ParameterType;

public class RandomTreeBuilder extends ClassifierBuilder{

	public RandomTreeBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}

	@Override
	public Classifier defaultBuild() {
		classifier = new RandomTree();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter m = new Parameter("M",ParameterType.INT);
		m.setValue("1");
		classifier.addParameter(m);
		
		Parameter k = new Parameter("K",ParameterType.INT);
		k.setValue("2");
		classifier.addParameter(k);
		
		Parameter depth = new Parameter("depth",ParameterType.INT);
		depth.setValue("2");
		classifier.addParameter(depth);
		
		Parameter n = new Parameter("N",ParameterType.INT);
		n.setValue("3");
		classifier.addParameter(n);
		
		Parameter u = new Parameter("U",ParameterType.BOOLEAN);
		u.setValue("false");
		classifier.addParameter(u);
		
		return classifier;
	}

	@Override
	public Classifier randomBuild() {
		classifier = new RandomTree();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter m = new Parameter("M",ParameterType.INT);
		m.setValue(randomValueForParameter(m));
		classifier.addParameter(m);
		
		Parameter k = new Parameter("K",ParameterType.INT);
		k.setValue(randomValueForParameter(k));
		classifier.addParameter(k);
		
		Parameter depth = new Parameter("depth",ParameterType.INT);
		depth.setValue(randomValueForParameter(depth));
		classifier.addParameter(depth);
		
		Parameter n = new Parameter("N",ParameterType.INT);
		n.setValue(randomValueForParameter(n));
		classifier.addParameter(n);
		
		Parameter u = new Parameter("U",ParameterType.BOOLEAN);
		u.setValue(randomValueForParameter(u));
		classifier.addParameter(u);
		return classifier;
	}

}
