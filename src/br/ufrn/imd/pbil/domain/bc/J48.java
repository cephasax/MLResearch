package br.ufrn.imd.pbil.domain.bc;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.enums.ClassifierType;

public class J48 extends Classifier{
	public J48(){
		super();
		this.name = "weka.classifiers.trees.J48";
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}

}
