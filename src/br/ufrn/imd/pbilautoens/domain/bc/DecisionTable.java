package br.ufrn.imd.pbilautoens.domain.bc;

import br.ufrn.imd.pbilautoens.Classifier;
import br.ufrn.imd.pbilautoens.enums.BaseClassifierType;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;

public class DecisionTable extends Classifier{

	public DecisionTable(){
		super();
		this.name = BaseClassifierType.DECISION_TABLE.getInfo();
		this.classifierType =ClassifierType.BASE_CLASSIFIER;
	}
	
}
