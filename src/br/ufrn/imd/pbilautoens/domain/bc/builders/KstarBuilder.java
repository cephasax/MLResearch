package br.ufrn.imd.pbilautoens.domain.bc.builders;

import br.ufrn.imd.pbilautoens.Classifier;
import br.ufrn.imd.pbilautoens.ClassifierBuilder;
import br.ufrn.imd.pbilautoens.ClassifierPrototype;
import br.ufrn.imd.pbilautoens.Parameter;
import br.ufrn.imd.pbilautoens.PbilRandom;
import br.ufrn.imd.pbilautoens.domain.bc.Kstar;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;
import br.ufrn.imd.pbilautoens.enums.ParameterType;

public class KstarBuilder extends ClassifierBuilder{

	public KstarBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}

	@Override
	public Classifier defaultBuild() {
		// TODO Auto-generated method stub
		classifier = new Kstar();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter b = new Parameter("B",ParameterType.INT);
		b.setValue("20");
		classifier.addParameter(b);
		
		Parameter e = new Parameter("E", ParameterType.BOOLEAN);
		e.setValue("false");
		classifier.addParameter(e);
		
		Parameter m = new Parameter("M", ParameterType.STRING);
		m.setValue("a");
		classifier.addParameter(m);
		return classifier;
	}

	@Override
	public Classifier randomBuild() {
		classifier = new Kstar();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter b = new Parameter("B", ParameterType.INT);
		b.setValue(randomValueForParameter(b));
		classifier.addParameter(b);
		
		Parameter e = new Parameter("E", ParameterType.BOOLEAN);
		e.setValue(randomValueForParameter(e));
		classifier.addParameter(e);
		
		Parameter m = new Parameter("M", ParameterType.STRING);
		m.setValue(randomValueForParameter(m));
		classifier.addParameter(m);
		
		return classifier;
	}
}
