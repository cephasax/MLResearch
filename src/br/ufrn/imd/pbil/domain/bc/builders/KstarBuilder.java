package br.ufrn.imd.pbil.domain.bc.builders;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.ClassifierBuilder;
import br.ufrn.imd.pbil.domain.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.bc.Kstar;
import br.ufrn.imd.pbil.enums.ParameterType;

public class KstarBuilder extends ClassifierBuilder{

	public KstarBuilder(ClassifierPrototype classifierPrototype) {
		super(classifierPrototype);
	}

	@Override
	public Classifier defaultBuild() {
		// TODO Auto-generated method stub
		classifier = new Kstar();
		classifier.setName("Default Kstar");
		Parameter b = new Parameter("B",ParameterType.INT);
		b.setValue("20");
		classifier.addParameter(b);
		
		Parameter e = new Parameter("E", ParameterType.BOOLEAN);
		e.setValue("false");
		classifier.addParameter(e);
		
		Parameter x = new Parameter("X", ParameterType.STRING);
		x.setValue("a");
		classifier.addParameter(x);
		return classifier;
	}

	@Override
	public Classifier randomBuild() {
		classifier = new Kstar();
		classifier.setName("Random Kstar");
		Parameter b = new Parameter("B", ParameterType.INT);
		b.setValue(randomValueForParameter(b));
		classifier.addParameter(b);
		
		Parameter e = new Parameter("E", ParameterType.BOOLEAN);
		e.setValue(randomValueForParameter(e));
		classifier.addParameter(e);
		
		Parameter x = new Parameter("X", ParameterType.STRING);
		x.setValue(randomValueForParameter(x));
		classifier.addParameter(x);
		
		return classifier;
	}
}
