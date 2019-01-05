package br.ufrn.imd.pbil.domain.bc.builders;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.ClassifierBuilder;
import br.ufrn.imd.pbil.domain.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.bc.DecisionTable;
import br.ufrn.imd.pbil.enums.ClassifierType;
import br.ufrn.imd.pbil.enums.ParameterType;

public class DecisionTableBuilder extends ClassifierBuilder {

	public DecisionTableBuilder(ClassifierPrototype classifierPrototype) {
		super(classifierPrototype);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Classifier defaultBuild() {
		classifier = new DecisionTable();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter e = new Parameter("E", ParameterType.STRING);
		e.setValue("acc");
		classifier.addParameter(e);
		
		Parameter s = new Parameter("S", ParameterType.STRING);
		s.setValue("GreedyStepwise -T -1.7976931348623157E308 -N 1 -num-slots 1");
		classifier.addParameter(s);
		
		Parameter i = new Parameter("I", ParameterType.BOOLEAN);
		i.setValue("false");
		classifier.addParameter(i);
		
		Parameter x = new Parameter("X", ParameterType.INT);
		x.setValue("1");
		classifier.addParameter(x);
		
		
		return classifier;
	}

	@Override
	public Classifier randomBuild() {
		classifier = new DecisionTable();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		Parameter e = new Parameter("E", ParameterType.STRING);
		e.setValue(randomValueForParameter(e));
		classifier.addParameter(e);
		
		Parameter s = new Parameter("S", ParameterType.STRING);
		s.setValue(randomValueForParameter(s));
		classifier.addParameter(s);
		
		Parameter i = new Parameter("I", ParameterType.BOOLEAN);
		i.setValue(randomValueForParameter(i));
		classifier.addParameter(i);
		
		Parameter x = new Parameter("X", ParameterType.INT);
		x.setValue(randomValueForParameter(x));
		classifier.addParameter(x);
		
		return classifier;
	}

}
