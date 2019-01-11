package br.ufrn.imd.pbilautoens.domain.bc.wekabuilders;

import br.ufrn.imd.pbilautoens.PossibilityKeySet;
import weka.classifiers.trees.J48;

public class J48WekaBuilder {

	public static J48 buildForWeka(PossibilityKeySet pks) {
		J48 j48 = new J48();
		
		j48.setUnpruned(Boolean.parseBoolean(pks.getKeyValuesPairs().get("U")));
		j48.setCollapseTree(Boolean.parseBoolean(pks.getKeyValuesPairs().get("O")));
		j48.setMinNumObj(Integer.parseInt(pks.getKeyValuesPairs().get("M")));
		j48.setConfidenceFactor(Float.parseFloat(pks.getKeyValuesPairs().get("C")));
		j48.setSubtreeRaising(Boolean.parseBoolean(pks.getKeyValuesPairs().get("S")));
		j48.setUseLaplace(Boolean.parseBoolean(pks.getKeyValuesPairs().get("A")));
		j48.setBinarySplits(Boolean.parseBoolean(pks.getKeyValuesPairs().get("B")));
		j48.setUseMDLcorrection(Boolean.parseBoolean(pks.getKeyValuesPairs().get("J")));
		
		
		return j48;
	}
	
}
