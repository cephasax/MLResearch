package br.ufrn.imd.pbil.domain.builders;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.NaiveBayes;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.prototypes.NaiveBayesPrototype;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

import java.util.Random;

public class NaiveBayesBuilder extends ClassifierBuilder{

	public Classifier defautBuild() {
		// TODO Auto-generated method stub
		classifier = new NaiveBayes("Naive Bayes");
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
		// TODO Auto-generated method stub
		classifier = new NaiveBayes("NaiveBayes");
		try {
			prototype = new NaiveBayesPrototype();
			List<Parameter> parameters = new ArrayList<Parameter>();
			
			Parameter d = new Parameter("D",ParameterType.BOOLEAN);
			
			Parameter k = new Parameter("K", ParameterType.BOOLEAN);
			
			d.setValue(randomValueForParameter(d));
			parameters.add(d);
			
			k.setValue(randomValueForParameter(k));
			parameters.add(k);
			
			classifier.setParameters(parameters);
		} catch (InvalidParameterTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classifier;
	}

	public Classifier wheitedDrawBuild() {
		// TODO Auto-generated method stub
		return null;
	}

}
