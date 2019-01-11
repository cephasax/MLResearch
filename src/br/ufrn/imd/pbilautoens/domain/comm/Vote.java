package br.ufrn.imd.pbilautoens.domain.comm;

import br.ufrn.imd.pbilautoens.Committee;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;
import br.ufrn.imd.pbilautoens.enums.CommitteeType;

public class Vote extends Committee{
	public Vote() {
		super();
		this.name = CommitteeType.VOTE.getInfo();
		this.classifierType = ClassifierType.COMMITTEE;
	}
}
