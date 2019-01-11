package br.ufrn.imd.pbilautoens.domain.bc;

import br.ufrn.imd.pbilautoens.Classifier;
import br.ufrn.imd.pbilautoens.enums.BaseClassifierType;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;

public class Kstar extends Classifier{
	public Kstar() {
		super();
		this.name = BaseClassifierType.K_STAR.getInfo();
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}

}
