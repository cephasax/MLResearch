package br.ufrn.imd.pbil.domain.comm.wekabuilders;

import br.ufrn.imd.pbil.domain.bc.wekabuilders.BaseClassifierWekaBuilder;
import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import weka.classifiers.meta.RandomCommittee;

public class RandomCommitteeWekaBuilder{
	

	public static RandomCommittee buildForWeka(PossibilityKeySet pks) {
		
		RandomCommittee rc = new RandomCommittee();
		rc.setNumIterations(Integer.valueOf(pks.getKeyValuesPairs().get("I")));
		rc.setSeed(Integer.valueOf(pks.getKeyValuesPairs().get("S")));
		
		rc.setClassifier(BaseClassifierWekaBuilder.buildClassifier(pks.getBranchClassifiers().get(0)));
		return rc;
	}
	
	
}
