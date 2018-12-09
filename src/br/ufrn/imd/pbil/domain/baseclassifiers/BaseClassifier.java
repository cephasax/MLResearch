package br.ufrn.imd.pbil.domain.baseclassifiers;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.enums.BaseClassifierType;

public class BaseClassifier extends Classifier {

	private BaseClassifierType baseClassifierType;

	public BaseClassifier() {
		super();
	}
	
	public BaseClassifierType getBaseClassifierType() {
		return baseClassifierType;
	}

	public void setBaseClassifierType(BaseClassifierType baseClassifierType) {
		this.baseClassifierType = baseClassifierType;
	}
	
}
