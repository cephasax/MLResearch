package br.ufrn.imd.pbilautoens.domain.bc;

import br.ufrn.imd.pbilautoens.Classifier;
import br.ufrn.imd.pbilautoens.enums.BaseClassifierType;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;

public class Smo extends Classifier {
	
	public Smo(){
		super();
		this.name = BaseClassifierType.SMO.getInfo();
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}

}
