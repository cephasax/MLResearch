package br.ufrn.imd.pbil.domain.builders;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.NaiveBayes;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.enums.ParameterType;
import java.util.Random;

public class NaiveBayesBuilder implements ClassifierBuilder{

	public Classifier defautBuild() {
		// TODO Auto-generated method stub
		NaiveBayes defautBayes = new NaiveBayes("Naive Bayes");
		List<Parameter> parameters = new ArrayList<Parameter>();
		Parameter d = new Parameter("D",ParameterType.BOOLEAN);
		d.setValue("false");
		parameters.add(d);
		d.setName("K");
		parameters.add(d);
		defautBayes.setParameters(parameters);
		return defautBayes;
	}

	public Classifier randomBuild() {
		// TODO Auto-generated method stub
		Random m = new Random();
		NaiveBayes randomBayes = new NaiveBayes("NaiveBayes");
		List<Parameter> parameters = new ArrayList<Parameter>();
		Parameter d = new Parameter("D",ParameterType.BOOLEAN);
		d.setValue(""+parameters.get(m.nextInt(1)));
		parameters.add(d);
		d.setName("K");
		d.setValue(""+parameters.get(m.nextInt(1)));
		parameters.add(d);
		randomBayes.setParameters(parameters);
		return randomBayes;
	}

	public Classifier wheitedDrawBuild() {
		// TODO Auto-generated method stub
		return null;
	}

}
