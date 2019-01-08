package br.ufrn.imd.pbil.domain.comm;

import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.enums.ClassifierType;
import br.ufrn.imd.pbil.enums.CommitteeType;

public class RandomForest extends Committee{
	public RandomForest() {
		super();
		this.name = CommitteeType.RANDOM_FOREST.getInfo();
		this.classifierType =ClassifierType.COMMITTEE;
	}
}
