package br.ufrn.imd.pbil.domain.bc;

import br.ufrn.imd.pbil.domain.Classifier;

public class NaiveBayes extends Classifier{
	public NaiveBayes( ) {
		super();
		this.name = "weka.classifiers.bayes.NaiveBayes";
	}

}
