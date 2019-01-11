package br.ufrn.imd.pbilautoens.domain.comm;

import br.ufrn.imd.pbilautoens.Committee;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;
import br.ufrn.imd.pbilautoens.enums.CommitteeType;

public class Stacking extends Committee{
	public Stacking() {
		super();
		this.name = CommitteeType.STACKING.getInfo();
		this.classifierType = ClassifierType.COMMITTEE;
	}
}
