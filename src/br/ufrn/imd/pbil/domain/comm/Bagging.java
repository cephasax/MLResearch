package br.ufrn.imd.pbil.domain.comm;

import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.enums.ClassifierType;
import br.ufrn.imd.pbil.enums.CommitteeType;

public class Bagging extends Committee{
	public Bagging() {
		super();
		this.name = CommitteeType.BAGGING.getInfo();
		this.classifierType =ClassifierType.COMMITTEE;
	}
}
