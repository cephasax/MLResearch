package br.ufrn.imd.pbil.domain;

import java.util.Random;

import br.ufrn.imd.pbil.domain.bc.BaseClassifierFactory;
import br.ufrn.imd.pbil.domain.comm.CommitteeFactory;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;
import br.ufrn.imd.pbil.pde.Possibility;
import br.ufrn.imd.pbil.pde.PossibilityFactory;

public class Factory {

	private ClassifierFactory baseclassifierFactory;
	private ClassifierFactory committeeFactory;
	private PossibilityFactory possibilityFactory;
	
	private Possibility firstLevel;
	private Possibility baseClassifierPossibilities;
	private Possibility committeePossibilities;
	private Possibility branchClassifierPossibilities;	
	private Random random;
	
	
	
	public Factory() throws InvalidParameterTypeException {
		this.baseclassifierFactory = new BaseClassifierFactory();
		this.committeeFactory = new CommitteeFactory();
		this.possibilityFactory = new PossibilityFactory();
		
		this.baseClassifierPossibilities = possibilityFactory.buildPossibilitiesForBaseClassifiers(
				baseclassifierFactory, "baseclassifierPossibilities");
		this.committeePossibilities = possibilityFactory.buildPossibilitiesForCommittes(
				committeeFactory, "committeePossibilities");
		this.branchClassifierPossibilities = possibilityFactory.buildPossibilitiesForBaseClassifiers(
				baseclassifierFactory, "branchClassifierPossibilities");
		
		buildFirstLevelPossibility();
		random = new Random();
	}
	
	public Classifier buildSolutionFromRandom() {
		int i = random.nextInt(this.firstLevel.getPossibilities().size());
		String name = this.firstLevel.getPossibilities().get(i).getKey();
		
		if(i <= 5) {
			return committeeFactory.buildClassifierRandomly(name);
		}
		else {
			int ii = random.nextInt(baseClassifierPossibilities.getPossibilities().size());
			String nameBase = this.baseClassifierPossibilities.getPossibilities().get(ii).getKey();
			return baseclassifierFactory.buildClassifierRandomly(nameBase);
		}
	}
	
	private void buildFirstLevelPossibility() {
		this.firstLevel = new Possibility("firstLevel");
		
		this.firstLevel.addPossibility(new Possibility("AdaBoost"));
		this.firstLevel.addPossibility(new Possibility("Bagging"));
		this.firstLevel.addPossibility(new Possibility("RandomCommittee"));
		this.firstLevel.addPossibility(new Possibility("RandomForest"));
		this.firstLevel.addPossibility(new Possibility("Stacking"));
		this.firstLevel.addPossibility(new Possibility("Vote"));
		this.firstLevel.addPossibility(new Possibility("BaseClassifier"));
	}
		
	public ClassifierFactory getBaseclassifierFactory() {
		return baseclassifierFactory;
	}

	public ClassifierFactory getCommitteeFactory() {
		return this.committeeFactory;
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

	public Possibility getCommitteePossibilities() {
		return committeePossibilities;
	}

	public void setCommitteePossibilities(Possibility committeePossibilities) {
		this.committeePossibilities = committeePossibilities;
	}

	
	public Possibility getBranchClassifierPossibilities() {
		return branchClassifierPossibilities;
	}

	
	public void setBranchClassifierPossibilities(Possibility branchClassifierPossibilities) {
		this.branchClassifierPossibilities = branchClassifierPossibilities;
	}

	
	public Possibility getFirstLevel() {
		return firstLevel;
	}

	
	public void setFirstLevel(Possibility firstLevel) {
		this.firstLevel = firstLevel;
	}
	
}
