package br.ufrn.imd.pbilautoens.domain.bc;

import br.ufrn.imd.pbilautoens.Classifier;
import br.ufrn.imd.pbilautoens.enums.BaseClassifierType;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;

public class Mlp extends Classifier{

	public Mlp(){
		super();
		this.name = BaseClassifierType.MULTI_LAYER_PECEPTRON.getInfo();
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}
}
