package br.ufrn.imd.pbil.domain.comm;

import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.enums.ClassifierType;

public class Vote extends Committee{
	public Vote() {
		super();
		this.name = "Vote";
		this.classifierType =ClassifierType.COMMITTEE;
	}
}
