package br.ufrn.imd.pbil.domain;

import java.util.ArrayList;
import java.util.Random;

import br.ufrn.imd.pbil.domain.bc.BaseClassifierFactory;
import br.ufrn.imd.pbil.domain.comm.CommitteeFactory;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;
import br.ufrn.imd.pbil.pde.Possibility;
import br.ufrn.imd.pbil.pde.PossibilityFactory;
import br.ufrn.imd.pbil.pde.PossibilityKeySet;

public class Factory {

	private ClassifierFactory baseclassifierFactory;
	private ClassifierFactory committeeFactory;
	private PossibilityFactory possibilityFactory;

	private Possibility firstLevel;
	private Possibility baseClassifierPossibilities;
	private Possibility committeePossibilities;
	private Possibility branchClassifierPossibilities;
	private Random random;
	private float learningRate;
	
	public Factory() throws InvalidParameterTypeException {
		this.baseclassifierFactory = new BaseClassifierFactory();
		this.committeeFactory = new CommitteeFactory();
		this.possibilityFactory = new PossibilityFactory();

		this.baseClassifierPossibilities = possibilityFactory
				.buildPossibilitiesForBaseClassifiers(baseclassifierFactory, "baseclassifierPossibilities");
		this.committeePossibilities = possibilityFactory.buildPossibilitiesForCommittes(committeeFactory,
				"committeePossibilities");
		this.branchClassifierPossibilities = possibilityFactory
				.buildPossibilitiesForBaseClassifiers(baseclassifierFactory, "branchClassifierPossibilities");

		buildFirstLevelPossibility();
		
		this.learningRate = 1;
		
		random = new Random();
	}
	
	public Classifier buildSolutionFromRandom() {
		int i =  random.nextInt(this.firstLevel.getPossibilities().size());
		String name = this.firstLevel.getPossibilities().get(i).getKey();

		if (i <= 5) {
			return committeeFactory.buildClassifierRandomly(name);
		} else {
			int ii = 7; //random.nextInt(baseClassifierPossibilities.getPossibilities().size());
			String nameBase = this.baseClassifierPossibilities.getPossibilities().get(ii).getKey();
			return baseclassifierFactory.buildClassifierRandomly(nameBase);
		}
	}
	
	public PossibilityKeySet buildSolutionFromWeightedDraw() {
		Possibility rootMethod = drawOneMethod(this.firstLevel);
		Possibility baseClassifier;
		Possibility committee;
		
		Possibility p = new Possibility();
		
		String name = new String(rootMethod.getKey());
		
		//IF WAS DRAWN A BASE CLASSIFIER
		if(name.equals("BaseClassifier")){
			baseClassifier = drawOneMethod(this.baseClassifierPossibilities);
			p = drawPossibility(baseClassifier);
			return new PossibilityKeySet(p);
		}
		//IF WAS A COMMITTEE
		else {
			int nBranch = 1;
			committee = this.committeePossibilities.findChildPossibility(name);
			p = drawPossibility(committee);
			PossibilityKeySet ready = new PossibilityKeySet(p);
			for(Possibility pNum: p.getPossibilities()) {
				if(pNum.getKey().equals("num")) {
					nBranch = Integer.valueOf(pNum.getDrawnValue());
				}
			}
			if(nBranch != 0) {
				ArrayList<PossibilityKeySet> pkss = new ArrayList<PossibilityKeySet>();
				for(int i = 0; i < nBranch; i++) {
					PossibilityKeySet pksTemp = new PossibilityKeySet(drawOneMethod(branchClassifierPossibilities));
					Possibility pTemp = branchClassifierPossibilities.findChildPossibility(pksTemp.getKey());
					pkss.add(new PossibilityKeySet(drawPossibility(pTemp)));
				}
				
				ready.setBranchClassifiers(pkss);
			}
			return ready;
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
	
	private Possibility drawPossibility(Possibility possibility) {
		Possibility drawed = new Possibility(possibility.getKey());	
		for(Possibility p: possibility.getPossibilities()) {
			drawed.addPossibility(drawOneChildFromPossibility(p));
		}
		return drawed;
	}
	
	private Possibility drawOneChildFromPossibility(Possibility possibility) {
		Possibility drawed = new Possibility(possibility.getKey());
		int aux = random.nextInt((int) possibility.getTotalWeight());
		for(Possibility p: possibility.getPossibilities()) {
			aux -= p.getWeight();
			if(aux < 0) {
				drawed.setDrawnValue(p.getKey());
				break;
			}
		}
		return drawed;
	}

	private Possibility drawOneMethod(Possibility possibility) {
		int aux = random.nextInt((int) possibility.getTotalWeight());
		Possibility drawed = new Possibility();
		for(Possibility p: possibility.getPossibilities()) {
			aux -= p.getWeight();
			if(aux < 0) {
				drawed = p;
				drawed.setDrawnValue(p.getKey());
				break;
			}
		}
		return drawed;
	}
	

	public PossibilityKeySet getPossibilitykeySetFromWeightedDraw(String classifierName, Possibility possibility) {
		Possibility drawed = new Possibility(classifierName);
		
		Possibility p = possibility.findChildPossibility(classifierName);
		if(p != null) {
			
			for(Possibility poss: p.getPossibilities()) {
				Possibility s = drawPossibility(poss);
				drawed.addPossibility(s);
			}
			return new PossibilityKeySet(drawed); 
		}
		else {
			return null;
		}
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

	public Possibility getBaseClassifierPossibilities() {
		return baseClassifierPossibilities;
	}

	public Possibility getCommitteePossibilities() {
		return committeePossibilities;
	}

	public Possibility getBranchClassifierPossibilities() {
		return branchClassifierPossibilities;
	}

	public Possibility getFirstLevel() {
		return firstLevel;
	}

	public float getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(float learningRate) {
		this.learningRate = learningRate;
	}
}
