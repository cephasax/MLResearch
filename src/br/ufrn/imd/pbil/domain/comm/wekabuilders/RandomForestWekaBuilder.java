package br.ufrn.imd.pbil.domain.comm.wekabuilders;

import br.ufrn.imd.pbil.domain.bc.wekabuilders.WekaBuilder;
import br.ufrn.imd.pbil.pde.PossibilityKeySet;
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
