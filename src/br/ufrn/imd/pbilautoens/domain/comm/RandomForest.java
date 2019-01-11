package br.ufrn.imd.pbilautoens.domain.comm;

import br.ufrn.imd.pbilautoens.Committee;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;
import br.ufrn.imd.pbilautoens.enums.CommitteeType;

public class RandomForest extends Committee{
	public RandomForest() {
		super();
		this.name = CommitteeType.RANDOM_FOREST.getInfo();
		this.classifierType =ClassifierType.COMMITTEE;
	}
}
