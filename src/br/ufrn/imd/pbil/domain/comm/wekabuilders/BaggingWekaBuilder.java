package br.ufrn.imd.pbil.domain.comm.wekabuilders;

import br.ufrn.imd.pbil.domain.bc.wekabuilders.WekaBuilder;
import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import weka.classifiers.meta.Bagging;

public class BaggingWekaBuilder {

	public static Bagging buildForWeka(PossibilityKeySet pks) {

		Bagging bag = new Bagging();
		bag.setBagSizePercent(Integer.valueOf(pks.getKeyValuesPairs().get("P")));
		bag.setNumIterations(Integer.valueOf(pks.getKeyValuesPairs().get("I")));
		bag.setSeed(Integer.valueOf(pks.getKeyValuesPairs().get("S")));
		bag.setCalcOutOfBag(Boolean.valueOf(pks.getKeyValuesPairs().get("O")));
		bag.setClassifier(WekaBuilder.buildClassifier(pks.getBranchClassifiers().get(0)));
		return bag;
	}

}
