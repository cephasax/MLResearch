package br.ufrn.imd.pbilautoens.domain.comm;

import br.ufrn.imd.pbilautoens.Committee;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;
import br.ufrn.imd.pbilautoens.enums.CommitteeType;

public class Bagging extends Committee{
	public Bagging() {
		super();
		this.name = CommitteeType.BAGGING.getInfo();
		this.classifierType =ClassifierType.COMMITTEE;
	}
}
