package br.ufrn.imd.pbil.domain.bc;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.enums.ClassifierType;

public class DecisionTable extends Classifier{

	public DecisionTable(){
		super();
		this.name = "weka.classifiers.rules.DecisionTable";
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}
	
}
