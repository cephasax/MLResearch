package br.ufrn.imd.pbil.domain.comm;

import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.enums.ClassifierType;
import br.ufrn.imd.pbil.enums.CommitteeType;

public class RandomCommittee extends Committee{

	public RandomCommittee(){
		super();
		this.name = CommitteeType.RANDOM_COMMITTEE.getInfo();
		this.classifierType = ClassifierType.COMMITTEE;
	}
	
}
