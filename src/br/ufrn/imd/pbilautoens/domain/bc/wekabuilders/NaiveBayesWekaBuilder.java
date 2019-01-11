package br.ufrn.imd.pbilautoens.domain.bc.wekabuilders;

import br.ufrn.imd.pbilautoens.PossibilityKeySet;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.GreedyStepwise;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.rules.DecisionTable;
import weka.core.SelectedTag;
import weka.core.Tag;

public class NaiveBayesWekaBuilder {

	public static NaiveBayes buildForWeka(PossibilityKeySet pks) {
		NaiveBayes nb = new NaiveBayes();
		
		nb.setUseKernelEstimator(Boolean.parseBoolean(pks.getKeyValuesPairs().get("K")));
		nb.setUseSupervisedDiscretization(Boolean.parseBoolean(pks.getKeyValuesPairs().get("D")));
		
		return nb;
	}
	
}
