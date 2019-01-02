package br.ufrn.imd.pbil.domain.bc;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.enums.BaseClassifierType;
import br.ufrn.imd.pbil.enums.ClassifierType;

public class BaseClassifier extends Classifier {

	private BaseClassifierType baseClassifierType;

	public BaseClassifier() {
		super();
		this.setClassifierType(ClassifierType.BASE_CLASSIFIER);
	}
	
	public BaseClassifierType getBaseClassifierType() {
		return baseClassifierType;
	}

	public void setBaseClassifierType(BaseClassifierType baseClassifierType) {
		this.baseClassifierType = baseClassifierType;
	}
	
}
