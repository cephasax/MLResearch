package br.ufrn.imd.pbil.domain.bc;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.enums.ClassifierType;

public class Ibk extends Classifier{
	public Ibk(){
		super();
		this.name = "IBkweka.classifiers.";
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}

}
