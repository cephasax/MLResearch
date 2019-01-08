package br.ufrn.imd.pbil.domain.bc.wekabuilders;

import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.GreedyStepwise;
import weka.classifiers.rules.DecisionTable;
import weka.core.SelectedTag;
import weka.core.Tag;

public class DecisionTableWekaBuilder {

	public static DecisionTable buildForWeka(PossibilityKeySet pks) {
		DecisionTable dt = new DecisionTable();
	
		Tag tag = new Tag();
		tag.setReadable(pks.getKeyValuesPairs().get("E"));
		Tag tags[] = new Tag[1];
		tags[0] = tag;
		dt.setEvaluationMeasure(new SelectedTag(0, tags));
		
		dt.setUseIBk(Boolean.valueOf(pks.getKeyValuesPairs().get("I")));
		dt.setSearch(buildASSearch(pks.getKeyValuesPairs().get("S")));
		dt.setCrossVal(Integer.valueOf(pks.getKeyValuesPairs().get("X")));
		
		return dt;
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
