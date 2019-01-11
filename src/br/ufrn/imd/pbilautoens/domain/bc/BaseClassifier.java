package br.ufrn.imd.pbilautoens.domain.bc;


import br.ufrn.imd.pbilautoens.Classifier;
import br.ufrn.imd.pbilautoens.enums.BaseClassifierType;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;

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
