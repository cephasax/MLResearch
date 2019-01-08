package br.ufrn.imd.pbil.douglas;

import br.ufrn.imd.pbil.domain.comm.wekabuilders.AdaBoostWekaBuilder;
import br.ufrn.imd.pbil.domain.comm.wekabuilders.BaggingWekaBuilder;
import br.ufrn.imd.pbil.domain.comm.wekabuilders.RandomCommitteeWekaBuilder;
import br.ufrn.imd.pbil.domain.comm.wekabuilders.RandomForestWekaBuilder;
import br.ufrn.imd.pbil.domain.comm.wekabuilders.StackingWekaBuilder;
import br.ufrn.imd.pbil.domain.comm.wekabuilders.VoteWekaBuilder;
import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import weka.classifiers.Classifier;
import weka.classifiers.meta.AdaBoostM1;

public class CommitteeBuilder {
	public static Classifier buildCommittee(PossibilityKeySet pks) throws NumberFormatException, Exception {
		switch(pks.getKey()) {
		case "Vote":
			return VoteWekaBuilder.buildForWeka(pks);
		case "Stacking":
			return StackingWekaBuilder.buildForWeka(pks);
		case "RandomCommittee":
			return RandomCommitteeWekaBuilder.buildForWeka(pks);
		case "RandomForest":
			return RandomForestWekaBuilder.buildForWeka(pks);
		case "AdaBoost":
			return AdaBoostWekaBuilder.buildForWeka(pks);
		case "Bagging":
			return BaggingWekaBuilder.buildForWeka(pks);
		}
		return null;
	}
}
