package br.ufrn.imd.pbil.domain.bc;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.enums.ClassifierType;

public class Smo extends Classifier {
	
	public Smo(){
		super();
		this.name = "weka.classifiers.functions.SMO";
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}

}
