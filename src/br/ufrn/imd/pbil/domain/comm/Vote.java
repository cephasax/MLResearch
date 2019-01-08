package br.ufrn.imd.pbil.domain.comm;

import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.enums.ClassifierType;
import br.ufrn.imd.pbil.enums.CommitteeType;

public class Vote extends Committee{
	public Vote() {
		super();
		this.name = CommitteeType.VOTE.getInfo();
		this.classifierType = ClassifierType.COMMITTEE;
	}
}
