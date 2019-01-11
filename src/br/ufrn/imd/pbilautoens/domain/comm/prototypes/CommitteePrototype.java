package br.ufrn.imd.pbilautoens.domain.comm.prototypes;

import br.ufrn.imd.pbilautoens.ClassifierPrototype;

public class CommitteePrototype extends ClassifierPrototype {

	private int numberOfBranchClassifiers;

	public int getNumberOfBranchClassifiers() {
		return numberOfBranchClassifiers;
	}

	public void setNumberOfBranchClassifiers(int numberOfBranchClassifiers) {
		this.numberOfBranchClassifiers = numberOfBranchClassifiers;
	}
	
}
