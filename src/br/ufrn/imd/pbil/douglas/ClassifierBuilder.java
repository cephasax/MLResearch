package br.ufrn.imd.pbil.douglas;

import br.ufrn.imd.pbil.domain.bc.wekabuilders.BayesNetWekaBuilder;
import br.ufrn.imd.pbil.domain.bc.wekabuilders.DecisionTableWekaBuilder;
import br.ufrn.imd.pbil.domain.bc.wekabuilders.IbkWekaBuilder;
import br.ufrn.imd.pbil.domain.bc.wekabuilders.J48WekaBuilder;
import br.ufrn.imd.pbil.domain.bc.wekabuilders.KstarWekaBuilder;
import br.ufrn.imd.pbil.domain.bc.wekabuilders.MlpWekaBuilder;
import br.ufrn.imd.pbil.domain.bc.wekabuilders.NaiveBayesWekaBuilder;
import br.ufrn.imd.pbil.domain.bc.wekabuilders.RandomTreeWekaBuilder;
import br.ufrn.imd.pbil.domain.bc.wekabuilders.SmoWekaBuilder;
import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import weka.classifiers.Classifier;

public class ClassifierBuilder {
	
	public static Classifier buildClassifier(PossibilityKeySet pks) {
		
		switch(pks.getKey()) {
			
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
		
		}
		return null;
	}
}
