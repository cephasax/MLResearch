package br.ufrn.imd.pbilautoens.domain.bc;

import br.ufrn.imd.pbilautoens.Classifier;
import br.ufrn.imd.pbilautoens.enums.BaseClassifierType;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;

public class NaiveBayes extends Classifier{
	public NaiveBayes( ) {
		super();
		this.name = BaseClassifierType.NAIVE_BAYES.getInfo();
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}

}
