package br.ufrn.imd.pbil.domain.bc.wekabuilders;

import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import weka.classifiers.Classifier;

public class BaseClassifierWekaBuilder {

	public static Classifier buildClassifier(PossibilityKeySet pks) {

		switch (pks.getKey()) {
			case "weka.classifiers.trees.J48":
				return J48WekaBuilder.buildForWeka(pks);
	
			case "weka.classifiers.trees.RandomTree":
				return RandomTreeWekaBuilder.buildForWeka(pks);
	
			case "weka.classifiers.rules.DecisionTable":
				return DecisionTableWekaBuilder.buildForWeka(pks);
	
			case "weka.classifiers.functions.MultilayerPerceptron":
				return MlpWekaBuilder.buildForWeka(pks);
	
			case "weka.classifiers.functions.SMO":
				return SmoWekaBuilder.buildForWeka(pks);
	
			case "weka.classifiers.lazy.IBk":
				return IbkWekaBuilder.buildForWeka(pks);
	
			case "weka.classifiers.lazy.KStar":
				return KstarWekaBuilder.buildForWeka(pks);
	
			case "weka.classifiers.bayes.NaiveBayes":
				return NaiveBayesWekaBuilder.buildForWeka(pks);
	
			case "weka.classifiers.bayes.BayesNet":
				return BayesNetWekaBuilder.buildForWeka(pks);
			default :
				return null;
			}
	}
}