package br.ufrn.imd.pbilautoens.domain.comm.wekabuilders;

import br.ufrn.imd.pbilautoens.PossibilityKeySet;
import br.ufrn.imd.pbilautoens.domain.bc.wekabuilders.WekaBuilder;
import weka.classifiers.meta.RandomCommittee;

public class RandomCommitteeWekaBuilder{
	
	public static RandomCommittee buildForWeka(PossibilityKeySet pks) {
		
		RandomCommittee rc = new RandomCommittee();
		rc.setNumIterations(Integer.valueOf(pks.getKeyValuesPairs().get("I")));
		rc.setSeed(Integer.valueOf(pks.getKeyValuesPairs().get("S")));
		
		rc.setClassifier(WekaBuilder.buildClassifier(pks.getBranchClassifiers().get(0)));
		return rc;
	}
	
	
}
