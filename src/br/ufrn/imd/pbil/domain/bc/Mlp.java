package br.ufrn.imd.pbil.domain.bc;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.enums.ClassifierType;

public class Mlp extends Classifier{

	public Mlp(){
		super();
		this.name = "weka.classifiers.functions.MultilayerPerceptron";
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}
}
