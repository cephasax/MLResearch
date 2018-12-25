package br.ufrn.imd.pbil.domain.comm.prototypes;

import br.ufrn.imd.pbil.domain.ClassifierPrototype;

public class CommitteePrototype extends ClassifierPrototype {

	private int numberOfBranchClassifiers;

	public int getNumberOfBranchClassifiers() {
		return numberOfBranchClassifiers;
	}

	public void setNumberOfBranchClassifiers(int numberOfBranchClassifiers) {
		this.numberOfBranchClassifiers = numberOfBranchClassifiers;
	}
	
}
