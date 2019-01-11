package br.ufrn.imd.pbilautoens.domain.comm.wekabuilders;

import br.ufrn.imd.pbilautoens.PossibilityKeySet;
import br.ufrn.imd.pbilautoens.domain.bc.wekabuilders.WekaBuilder;
import weka.classifiers.trees.RandomForest;

public class RandomForestWekaBuilder {

	public static RandomForest buildForWeka(PossibilityKeySet pks) {
		
		RandomForest rf = new RandomForest();

		rf.setNumIterations(Integer.parseInt(pks.getKeyValuesPairs().get("I")));
		rf.setNumFeatures(Integer.parseInt(pks.getKeyValuesPairs().get("K")));
		rf.setClassifier(WekaBuilder.buildClassifier(pks.getBranchClassifiers().get(0)));

		return rf;
	}

}
