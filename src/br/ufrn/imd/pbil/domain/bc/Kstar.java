package br.ufrn.imd.pbil.domain.bc;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.enums.BaseClassifierType;
import br.ufrn.imd.pbil.enums.ClassifierType;

public class Kstar extends Classifier{
	public Kstar() {
		super();
		this.name = BaseClassifierType.K_STAR.getInfo();
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}

}
