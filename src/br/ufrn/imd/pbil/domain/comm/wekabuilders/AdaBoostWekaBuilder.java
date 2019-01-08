package br.ufrn.imd.pbil.domain.comm.wekabuilders;

import br.ufrn.imd.pbil.domain.bc.wekabuilders.BaseClassifierWekaBuilder;
import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import weka.classifiers.meta.AdaBoostM1;

public class AdaBoostWekaBuilder {

	public static AdaBoostM1 buildforWeka(PossibilityKeySet pks) {
		AdaBoostM1 ab = new AdaBoostM1();

		ab.setUseResampling(Boolean.parseBoolean(pks.getKeyValuesPairs().get("Q")));
		ab.setSeed(Integer.parseInt(pks.getKeyValuesPairs().get("S")));
		ab.setNumIterations(Integer.parseInt(pks.getKeyValuesPairs().get("I")));
		ab.setWeightThreshold(Integer.parseInt(pks.getKeyValuesPairs().get("P")));
		ab.setClassifier(BaseClassifierWekaBuilder.buildClassifier(pks.getBranchClassifiers().get(0)));

		return ab;
	}

}