package br.ufrn.imd.pbil.domain.bc.wekabuilders;

import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.GreedyStepwise;
import weka.classifiers.trees.RandomTree;

public class RandomTreeWekaBuilder {

	public static RandomTree buildWekaRandomTree(PossibilityKeySet pks) {
		RandomTree rt = new RandomTree();
		
		rt.setKValue(Integer.parseInt(pks.getKeyValuesPairs().get("K")));
		rt.setMaxDepth(Integer.parseInt(pks.getKeyValuesPairs().get("depth")));
		rt.setMinNum(Integer.parseInt(pks.getKeyValuesPairs().get("M")));
		rt.setAllowUnclassifiedInstances(Boolean.parseBoolean(pks.getKeyValuesPairs().get("U")));
		rt.setNumFolds(Integer.parseInt(pks.getKeyValuesPairs().get("N")));
		
		return rt;
	}
	
	private static ASSearch buildASSearch(String s) {
		switch (s) {
			case "BestFirst -D 1 -N 5":{
				return new BestFirst();
			}
			case "GreedyStepwise -T -1.7976931348623157E308 -N 1 -num-slots 1":{
				return new GreedyStepwise();
			}
			default:
				return new BestFirst();
			}
	}
	
}
