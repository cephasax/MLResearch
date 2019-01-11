package br.ufrn.imd.pbilautoens.domain.bc;

import br.ufrn.imd.pbilautoens.Classifier;
import br.ufrn.imd.pbilautoens.enums.BaseClassifierType;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;

public class Ibk extends Classifier{
	public Ibk(){
		super();
		this.name = BaseClassifierType.IBK.getInfo();
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}

}
