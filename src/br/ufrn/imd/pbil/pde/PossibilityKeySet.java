package br.ufrn.imd.pbil.pde;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.ufrn.imd.pbil.annotations.ToFix;
import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.domain.Parameter;

public class PossibilityKeySet {

	private String key;
	private Map<String, String> keyValuesPairs;
	private ArrayList<PossibilityKeySet> branchClassifiers;
	
	@ToFix
	public PossibilityKeySet(Classifier classifier) {
		
		this.keyValuesPairs = new HashMap<String, String>();
		this.key = classifier.getName();
		
		for(Parameter param: classifier.getParameters()) {
			keyValuesPairs.put(param.getName(), param.getValue());
		}
		
		if(classifier.getClass() == Committee.class) {
			Committee committee = (Committee)classifier;
			
			for(Classifier c: committee.getClassifiers()) {
				this.branchClassifiers.add(buildBranchClassifier(c));
			}
		}
	}
	
	public PossibilityKeySet(Possibility possibility) {
		this.keyValuesPairs = new HashMap<String, String>();
		this.key = possibility.getKey();
		for(Possibility poss: possibility.getPossibilities()) {
			keyValuesPairs.put(poss.getKey(), poss.getPossibilities().get(0).getKey());
		}
	}
	
	public PossibilityKeySet(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map<String, String> getKeyValuesPairs() {
		return keyValuesPairs;
	}

	public void setKeyValuesPairs(Map<String, String> keyValuesPairs) {
		this.keyValuesPairs = keyValuesPairs;
	}
	
	private PossibilityKeySet buildBranchClassifier(Classifier classifier) {
		PossibilityKeySet p = new PossibilityKeySet(classifier.getName());
		for(Parameter param: classifier.getParameters()) {
			p.keyValuesPairs.put(param.getName(), param.getValue());
		}
		return p;
	}

	@Override
	public String toString() {
		return "PossibilityKeySet [key=" + key + ", keyValuesPairs=" + keyValuesPairs + ", branchClassifiers="
				+ branchClassifiers + "]";
	}

	
	
}
