package br.ufrn.imd.pbil.domain.comm;

import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.enums.ClassifierType;
import br.ufrn.imd.pbil.enums.CommitteeType;

public class Stacking extends Committee{
	public Stacking() {
		super();
		this.name = CommitteeType.STACKING.getInfo();
		this.classifierType = ClassifierType.COMMITTEE;
	}
}
