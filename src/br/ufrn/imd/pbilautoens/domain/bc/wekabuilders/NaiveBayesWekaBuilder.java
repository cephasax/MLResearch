package br.ufrn.imd.pbilautoens.domain.bc.wekabuilders;

import br.ufrn.imd.pbilautoens.PossibilityKeySet;
import weka.classifiers.bayes.NaiveBayes;

public class NaiveBayesWekaBuilder {

	public static NaiveBayes buildForWeka(PossibilityKeySet pks) {
		NaiveBayes nb = new NaiveBayes();
		
		nb.setUseKernelEstimator(Boolean.parseBoolean(pks.getKeyValuesPairs().get("K")));
		nb.setUseSupervisedDiscretization(Boolean.parseBoolean(pks.getKeyValuesPairs().get("D")));
		
		return nb;
	}
	
}
