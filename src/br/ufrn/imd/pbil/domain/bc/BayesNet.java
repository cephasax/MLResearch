package br.ufrn.imd.pbil.domain.bc;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.enums.BaseClassifierType;
import br.ufrn.imd.pbil.enums.ClassifierType;

public class BayesNet extends Classifier{
	public BayesNet() {
		// TODO Auto-generated constructor stub
		super();
		this.name = BaseClassifierType.NET.getInfo();
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}
	

}
