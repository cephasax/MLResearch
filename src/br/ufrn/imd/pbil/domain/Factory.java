package br.ufrn.imd.pbil.domain;

import br.ufrn.imd.pbil.domain.committees.CommitteeFactory;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;
import br.ufrn.imd.pbil.pde.Possibility;
import br.ufrn.imd.pbil.pde.PossibilityFactory;

public class Factory {

	private ClassifierFactory baseclassifierFactory;
	private ClassifierFactory committeeFactory;
	private PossibilityFactory possibilityFactory;
	
	private Possibility baseClassifierPossibilities;
	//private Possibility committeePossibilities;
	
	public Factory() throws InvalidParameterTypeException {
		this.baseclassifierFactory = new BaseClassifierFactory();
		this.committeeFactory = new CommitteeFactory();
		this.possibilityFactory = new PossibilityFactory();
		
		this.baseClassifierPossibilities = possibilityFactory.buildPossibilities(baseclassifierFactory, "baseclassifierPossibilities");
		//this.committeePossibilities = possibilityFactory.buildPossibilities(committeeFactory, "committeePossibilities");
	}
	
	public ClassifierFactory getBaseclassifierFactory() {
		return baseclassifierFactory;
	}

	public void setBaseclassifierFactory(ClassifierFactory baseclassifierFactory) {
		this.baseclassifierFactory = baseclassifierFactory;
	}

	public ClassifierFactory getCommitteeFactory() {
		return this.committeeFactory;
	}

	public void setCommitteeFactory(ClassifierFactory committeeFactory) {
		this.committeeFactory = committeeFactory;
	}

	public PossibilityFactory getPossibilityFactory() {
		return possibilityFactory;
	}

	public void setPossibilityFactory(PossibilityFactory possibilityFactory) {
		this.possibilityFactory = possibilityFactory;
	}

	public Possibility getBaseClassifierPossibilities() {
		return baseClassifierPossibilities;
	}

	public void setBaseClassifierPossibilities(Possibility baseClassifierPossibilities) {
		this.baseClassifierPossibilities = baseClassifierPossibilities;
	}
	
	
}
