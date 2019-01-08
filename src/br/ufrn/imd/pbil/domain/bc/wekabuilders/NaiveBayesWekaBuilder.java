package br.ufrn.imd.pbil.domain.bc.wekabuilders;

import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.GreedyStepwise;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.rules.DecisionTable;
import weka.core.SelectedTag;
import weka.core.Tag;

public class NaiveBayesWekaBuilder {

	public static NaiveBayes buildWekaNaiveBayes(PossibilityKeySet pks) {
		NaiveBayes nb = new NaiveBayes();
		
		nb.setUseKernelEstimator(Boolean.parseBoolean(pks.getKeyValuesPairs().get("K")));
		nb.setUseSupervisedDiscretization(Boolean.parseBoolean(pks.getKeyValuesPairs().get("D")));
		
		return nb;
	}
	
}
