package br.ufrn.imd.pbil.domain.comm;

import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.enums.ClassifierType;

public class RandomForest extends Committee{
	public RandomForest() {
		super();
		this.name = "RandomForest";
		this.classifierType =ClassifierType.COMMITTEE;
	}
}
