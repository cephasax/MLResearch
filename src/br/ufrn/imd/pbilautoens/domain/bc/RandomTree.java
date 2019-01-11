package br.ufrn.imd.pbilautoens.domain.bc;

import br.ufrn.imd.pbilautoens.Classifier;
import br.ufrn.imd.pbilautoens.enums.BaseClassifierType;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;

public class RandomTree extends Classifier{
	public RandomTree() {
		super();
		this.name = BaseClassifierType.RANDOM_TREE.getInfo();
		this.classifierType = ClassifierType.BASE_CLASSIFIER;
	}
}
