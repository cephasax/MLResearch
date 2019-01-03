package br.ufrn.imd.pbil.domain.bc.builders;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.ClassifierBuilder;
import br.ufrn.imd.pbil.domain.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.bc.Ibk;
import br.ufrn.imd.pbil.enums.ClassifierType;
import br.ufrn.imd.pbil.enums.ParameterType;

public class IbkBuilder extends ClassifierBuilder{

	public IbkBuilder(ClassifierPrototype classifierPrototype) {
		super(classifierPrototype);
	}

	@Override
	public Classifier defaultBuild() {
		classifier = new Ibk();
		classifier.setName("IBK");
		Parameter e = new Parameter("E", ParameterType.BOOLEAN);
		e.setValue("false");
		classifier.addParameter(e);
		
		Parameter k = new Parameter("K",ParameterType.INT);
		k.setValue("1");
		classifier.addParameter(k);
		
		Parameter i = new Parameter("I", ParameterType.BOOLEAN);
		i.setValue("false");
		classifier.addParameter(i);
		
		Parameter f = new Parameter("F", ParameterType.BOOLEAN);
		f.setValue("false");
		classifier.addParameter(f);
		
		Parameter x = new Parameter("X", ParameterType.BOOLEAN);
		x.setValue("false");
		classifier.addParameter(x);
		
		return classifier;
	}

	@Override
	public Classifier randomBuild() {
		classifier = new Ibk();
		classifier.setName("IBK");
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter e = new Parameter("E", ParameterType.BOOLEAN);
		e.setValue(randomValueForParameter(e));
		classifier.addParameter(e);
		
		Parameter k = new Parameter("K",ParameterType.INT);
		k.setValue(randomValueForParameter(k));
		classifier.addParameter(k);
		
		Parameter i = new Parameter("I", ParameterType.BOOLEAN);
		i.setValue(randomValueForParameter(i));
		classifier.addParameter(i);
		
		Parameter f = new Parameter("F", ParameterType.BOOLEAN);
		f.setValue(randomValueForParameter(f));
		classifier.addParameter(f);
		
		Parameter x = new Parameter("X", ParameterType.BOOLEAN);
		x.setValue(randomValueForParameter(x));
		classifier.addParameter(x);
		
		return classifier;
	}

}
