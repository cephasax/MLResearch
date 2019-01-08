package br.ufrn.imd.pbil.domain.bc;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.enums.BaseClassifierType;
import br.ufrn.imd.pbil.enums.ClassifierType;

public class J48 extends Classifier{
	public J48(){
		super();
		this.name = BaseClassifierType.J48.getInfo();
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}

}
