package br.ufrn.imd.pbilautoens.domain.comm.wekabuilders;

import br.ufrn.imd.pbilautoens.PossibilityKeySet;
import br.ufrn.imd.pbilautoens.domain.bc.wekabuilders.WekaBuilder;
import weka.classifiers.meta.AdaBoostM1;

public class AdaBoostWekaBuilder {

	public static AdaBoostM1 buildForWeka(PossibilityKeySet pks) {
		AdaBoostM1 ab = new AdaBoostM1();

		ab.setUseResampling(Boolean.parseBoolean(pks.getKeyValuesPairs().get("Q")));
		ab.setSeed(Integer.parseInt(pks.getKeyValuesPairs().get("S")));
		ab.setNumIterations(Integer.parseInt(pks.getKeyValuesPairs().get("I")));
		ab.setWeightThreshold(Integer.parseInt(pks.getKeyValuesPairs().get("P")));
		ab.setClassifier(WekaBuilder.buildClassifier(pks.getBranchClassifiers().get(0)));

		return ab;
	}

}
