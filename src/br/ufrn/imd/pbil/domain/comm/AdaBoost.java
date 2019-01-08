package br.ufrn.imd.pbil.domain.comm;

import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.enums.ClassifierType;
import br.ufrn.imd.pbil.enums.CommitteeType;

public class AdaBoost extends Committee{
	public AdaBoost() {
		super();
		this.name = CommitteeType.ADA_BOOST.getInfo();
		this.classifierType = ClassifierType.COMMITTEE;
	}
}
