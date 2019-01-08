package br.ufrn.imd.pbil.domain.bc;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.enums.BaseClassifierType;
import br.ufrn.imd.pbil.enums.ClassifierType;

public class Smo extends Classifier {
	
	public Smo(){
		super();
		this.name = BaseClassifierType.SMO.getInfo();
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}

}
