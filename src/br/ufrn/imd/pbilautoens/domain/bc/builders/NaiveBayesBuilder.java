package br.ufrn.imd.pbilautoens.domain.bc.builders;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pbilautoens.Classifier;
import br.ufrn.imd.pbilautoens.ClassifierBuilder;
import br.ufrn.imd.pbilautoens.ClassifierPrototype;
import br.ufrn.imd.pbilautoens.Parameter;
import br.ufrn.imd.pbilautoens.PbilRandom;
import br.ufrn.imd.pbilautoens.domain.bc.NaiveBayes;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;
import br.ufrn.imd.pbilautoens.enums.ParameterType;

public class NaiveBayesBuilder extends ClassifierBuilder{

	public NaiveBayesBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}

	public Classifier defaultBuild() {
		classifier = new NaiveBayes();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		List<Parameter> parameters = new ArrayList<Parameter>();
		
		Parameter d = new Parameter("D",ParameterType.BOOLEAN);
		d.setValue("false");
		parameters.add(d);
		
		d.setName("K");
		parameters.add(d);
		
		classifier.setParameters(parameters);
		
		return classifier;
	}

	public Classifier randomBuild() {
		classifier = new NaiveBayes();
		classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
		List<Parameter> parameters = new ArrayList<Parameter>();
		
		Parameter d = new Parameter("D",ParameterType.BOOLEAN);
		d.setValue(randomValueForParameter(d));
		parameters.add(d);
		
		Parameter k = new Parameter("K", ParameterType.BOOLEAN);
		k.setValue(randomValueForParameter(k));
		parameters.add(k);
		
		classifier.setParameters(parameters); 
		return classifier;
	}

	public Classifier wheitedDrawBuild() {
		return null;
	}
}
