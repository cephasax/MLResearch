package br.ufrn.imd.pbilautoens.domain.bc;

import br.ufrn.imd.pbilautoens.Classifier;
import br.ufrn.imd.pbilautoens.enums.BaseClassifierType;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;

public class BayesNet extends Classifier{
	public BayesNet() {
		// TODO Auto-generated constructor stub
		super();
		this.name = BaseClassifierType.NET.getInfo();
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}
	

}
