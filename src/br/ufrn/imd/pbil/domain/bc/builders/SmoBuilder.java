package br.ufrn.imd.pbil.domain.bc.builders;

import java.util.List;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.ClassifierBuilder;
import br.ufrn.imd.pbil.domain.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.bc.Smo;
import br.ufrn.imd.pbil.enums.ClassifierType;
import br.ufrn.imd.pbil.enums.ParameterType;

public class SmoBuilder extends ClassifierBuilder{

	public SmoBuilder(ClassifierPrototype classifierPrototype) {
		super(classifierPrototype);
	}

	public Classifier classifier;
	
	public Classifier defaultBuild() {
		classifier = new Smo();
		classifier.setName("SMO");
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
