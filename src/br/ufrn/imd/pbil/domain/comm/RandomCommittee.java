package br.ufrn.imd.pbil.domain.comm;

import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.enums.ClassifierType;

public class RandomCommittee extends Committee{

	public RandomCommittee(){
		super();
		this.name = "RandomCommittee";
		this.classifierType =ClassifierType.COMMITTEE;
	}
	
}
