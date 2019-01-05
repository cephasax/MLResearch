package br.ufrn.imd.pbil.domain.bc;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.enums.ClassifierType;

public class NaiveBayes extends Classifier{
	public NaiveBayes( ) {
		super();
		this.name = "weka.classifiers.bayes.NaiveBayes";
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}

}
