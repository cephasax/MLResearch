package br.ufrn.imd.pbil.domain.bc;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.enums.BaseClassifierType;
import br.ufrn.imd.pbil.enums.ClassifierType;

public class Mlp extends Classifier{

	public Mlp(){
		super();
		this.name = BaseClassifierType.MULTI_LAYER_PECEPTRON.getInfo();
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}
}
