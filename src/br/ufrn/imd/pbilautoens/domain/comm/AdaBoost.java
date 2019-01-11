package br.ufrn.imd.pbilautoens.domain.comm;

import br.ufrn.imd.pbilautoens.Committee;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;
import br.ufrn.imd.pbilautoens.enums.CommitteeType;

public class AdaBoost extends Committee{
	public AdaBoost() {
		super();
		this.name = CommitteeType.ADA_BOOST.getInfo();
		this.classifierType = ClassifierType.COMMITTEE;
	}
}
