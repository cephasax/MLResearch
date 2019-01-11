package br.ufrn.imd.pbilautoens.domain.comm;

import br.ufrn.imd.pbilautoens.Committee;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;
import br.ufrn.imd.pbilautoens.enums.CommitteeType;

public class RandomCommittee extends Committee{

	public RandomCommittee(){
		super();
		this.name = CommitteeType.RANDOM_COMMITTEE.getInfo();
		this.classifierType = ClassifierType.COMMITTEE;
	}
	
}
