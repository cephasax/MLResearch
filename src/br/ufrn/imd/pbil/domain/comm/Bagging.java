package br.ufrn.imd.pbil.domain.comm;

import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.enums.ClassifierType;

public class Bagging extends Committee{
	public Bagging() {
		super();
		this.name = "Bagging";
		this.classifierType =ClassifierType.COMMITTEE;
		this.setBranchClassifierParameter("-W");
	}
}
