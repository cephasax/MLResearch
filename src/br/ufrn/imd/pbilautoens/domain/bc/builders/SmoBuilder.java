package br.ufrn.imd.pbilautoens.domain.bc.builders;

import java.util.List;

import br.ufrn.imd.pbilautoens.Classifier;
import br.ufrn.imd.pbilautoens.ClassifierBuilder;
import br.ufrn.imd.pbilautoens.ClassifierPrototype;
import br.ufrn.imd.pbilautoens.Parameter;
import br.ufrn.imd.pbilautoens.PbilRandom;
import br.ufrn.imd.pbilautoens.domain.bc.Smo;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;
import br.ufrn.imd.pbilautoens.enums.ParameterType;

public class SmoBuilder extends ClassifierBuilder{

	public SmoBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}

	public Classifier classifier;
	
	public Classifier defaultBuild() {
		classifier = new Smo();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter u = new Parameter("SEL",ParameterType.STRING);
		u.setValue("Defaut");
		List<Parameter> params = classifier.getParameters();
		params.add(u);
		classifier.setParameters(params);
		return classifier;
	}

	public Classifier randomBuild() {
		classifier = new Smo();
		classifier.setName("SMO");
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter u = new Parameter("SEL",ParameterType.STRING);
		u.setValue(randomValueForParameter(u));
		classifier.addParameter(u);
		return classifier;
	}

}
