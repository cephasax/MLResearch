package br.ufrn.imd.pbil.domain.bc;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.enums.BaseClassifierType;
import br.ufrn.imd.pbil.enums.ClassifierType;

public class RandomTree extends Classifier{
	public RandomTree() {
		super();
		this.name = BaseClassifierType.RANDOM_TREE.getInfo();
		this.classifierType = ClassifierType.BASE_CLASSIFIER;
	}
}
