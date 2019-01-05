package br.ufrn.imd.pbil.domain.bc;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.enums.ClassifierType;

public class RandomTree extends Classifier{
	public RandomTree() {
		super();
		this.name = "weka.classifiers.trees.RandomTree";
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}

}
