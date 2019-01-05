package br.ufrn.imd.pbil.domain.comm;

import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.enums.ClassifierType;

public class AdaBoost extends Committee{
	public AdaBoost() {
		super();
		this.name = "AdaBoost";
		this.classifierType =ClassifierType.COMMITTEE;
	}
}
