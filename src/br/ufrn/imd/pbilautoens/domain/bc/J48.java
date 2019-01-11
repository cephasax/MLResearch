package br.ufrn.imd.pbilautoens.domain.bc;

import br.ufrn.imd.pbilautoens.Classifier;
import br.ufrn.imd.pbilautoens.enums.BaseClassifierType;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;

public class J48 extends Classifier{
	public J48(){
		super();
		this.name = BaseClassifierType.J48.getInfo();
		this.classifierType = ClassifierType.BASE_CLASSIFIER;
	}

}
