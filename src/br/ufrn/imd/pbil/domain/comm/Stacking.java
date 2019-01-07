package br.ufrn.imd.pbil.domain.comm;

import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.enums.ClassifierType;

public class Stacking extends Committee{
	public Stacking() {
		super();
		this.name = "Stacking";
		this.classifierType =ClassifierType.COMMITTEE;
		this.setBranchClassifierParameter("-M");
	}
}
