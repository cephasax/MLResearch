package br.ufrn.imd.pbilautoens;

import java.util.ArrayList;
import java.util.Map;

import br.ufrn.imd.pbilautoens.domain.bc.BaseClassifierFactory;
import br.ufrn.imd.pbilautoens.domain.comm.CommitteeFactory;
import br.ufrn.imd.pbilautoens.enums.BaseClassifierType;
import br.ufrn.imd.pbilautoens.enums.ClassifierType;
import br.ufrn.imd.pbilautoens.enums.CommitteeType;
import br.ufrn.imd.pbilautoens.exception.InvalidParameterTypeException;

public class Factory {

	private ClassifierFactory baseclassifierFactory;
	private ClassifierFactory committeeFactory;
	private PossibilityFactory possibilityFactory;

	private Possibility firstLevel;
	private Possibility baseClassifierPossibilities;
	private Possibility committeePossibilities;
	private Possibility branchClassifierPossibilities;
	private float learningRate;
	private PbilRandom pbilRandom;
	
	public Factory(PbilRandom pbilRandom) throws InvalidParameterTypeException {
		
		this.pbilRandom = pbilRandom;
		
		this.baseclassifierFactory = new BaseClassifierFactory(pbilRandom);
		this.committeeFactory = new CommitteeFactory(pbilRandom);
		this.possibilityFactory = new PossibilityFactory();

		this.baseClassifierPossibilities = possibilityFactory
				.buildPossibilitiesForBaseClassifiers(baseclassifierFactory, "baseclassifierPossibilities");
		this.committeePossibilities = possibilityFactory.buildPossibilitiesForCommittes(committeeFactory,
				"committeePossibilities");
		this.branchClassifierPossibilities = possibilityFactory
				.buildPossibilitiesForBaseClassifiers(baseclassifierFactory, "branchClassifierPossibilities");

		buildFirstLevelPossibility();
		
		this.learningRate = 1;
	}
	
	public Classifier buildSolutionFromRandom() {
		int i =  pbilRandom.nextInt(this.firstLevel.getPossibilities().size());
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
		if(name.equals(ClassifierType.BASE_CLASSIFIER.getInfo())){
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
			ArrayList<PossibilityKeySet> pkss = new ArrayList<PossibilityKeySet>();
			
			if(nBranch != 0 && !name.equals(CommitteeType.RANDOM_FOREST.getInfo())) {
				for(int i = 0; i < nBranch; i++) {
					PossibilityKeySet pksTemp = new PossibilityKeySet(drawOneMethod(branchClassifierPossibilities));
					Possibility pTemp = branchClassifierPossibilities.findChildPossibility(pksTemp.getKey());
					pkss.add(new PossibilityKeySet(drawPossibility(pTemp)));
				}
			}
			else {
				Possibility rt = findChildPossibility(BaseClassifierType.RANDOM_TREE.getInfo(), branchClassifierPossibilities);
				pkss.add(new PossibilityKeySet(drawPossibility(rt)));
			}
			ready.setBranchClassifiers(pkss);
			return ready;
		}
}
	
	private void buildFirstLevelPossibility() {
		this.firstLevel = new Possibility("firstLevel");

		this.firstLevel.addPossibility(new Possibility(CommitteeType.ADA_BOOST.getInfo()));
		this.firstLevel.addPossibility(new Possibility(CommitteeType.BAGGING.getInfo()));
		this.firstLevel.addPossibility(new Possibility(CommitteeType.RANDOM_COMMITTEE.getInfo()));
		this.firstLevel.addPossibility(new Possibility(CommitteeType.RANDOM_FOREST.getInfo()));
		this.firstLevel.addPossibility(new Possibility(CommitteeType.STACKING.getInfo()));
		this.firstLevel.addPossibility(new Possibility(CommitteeType.VOTE.getInfo()));
		this.firstLevel.addPossibility(new Possibility(ClassifierType.BASE_CLASSIFIER.getInfo()));
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
		int aux = pbilRandom.nextInt((int) possibility.getTotalWeight());
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
		int aux = pbilRandom.nextInt((int) possibility.getTotalWeight());
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
	
	public void updatePossibilities(PossibilityKeySet possibilityKeySet) {
		if(possibilityKeySet.getBranchClassifiers().size() > 0) {
			
			Possibility first = findChildPossibility(possibilityKeySet.getKey(), this.firstLevel);
			first.increaseWeight(learningRate);
			this.firstLevel.updateWeights();
			
			Possibility method = findChildPossibility(possibilityKeySet.getKey(), this.committeePossibilities);
			method.increaseWeight(learningRate);
			this.committeePossibilities.updateWeights();
			for(Map.Entry<String, String> s: possibilityKeySet.getKeyValuesPairs().entrySet()) {
				Possibility param = findChildPossibility(s.getKey(), method);
				Possibility value = findChildPossibility(s.getValue(), param);
				value.increaseWeight(learningRate);
				param.updateWeights();
			}
			
			for(PossibilityKeySet pks: possibilityKeySet.getBranchClassifiers()) {
				Possibility branchMethod = findChildPossibility(pks.getKey(), this.branchClassifierPossibilities);
				branchMethod.increaseWeight(learningRate);
				this.branchClassifierPossibilities.updateWeights();
				
				for(Map.Entry<String, String> s: pks.getKeyValuesPairs().entrySet()) {
					Possibility branchParam = findChildPossibility(s.getKey(), branchMethod);
					Possibility branchParamValue = findChildPossibility(s.getValue(), branchParam);
					branchParamValue.increaseWeight(learningRate);
					branchParam.updateWeights();
				}
			}
		}
		else {
			
			Possibility first = findChildPossibility(ClassifierType.BASE_CLASSIFIER.getInfo(), this.firstLevel);
			first.increaseWeight(learningRate);
			this.firstLevel.updateWeights();
			
			Possibility method = findChildPossibility(possibilityKeySet.getKey(), this.baseClassifierPossibilities);
			method.increaseWeight(learningRate);
			this.baseClassifierPossibilities.updateWeights();
			for(Map.Entry<String, String> s: possibilityKeySet.getKeyValuesPairs().entrySet()) {
				Possibility param = findChildPossibility(s.getKey(), method);
				Possibility value = findChildPossibility(s.getValue(), param);
				value.increaseWeight(learningRate);
				param.updateWeights();
			}
		}
	}
	
	private Possibility findChildPossibility(String MethodName, Possibility possibility) {
		return possibility.findChildPossibility(MethodName);
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
