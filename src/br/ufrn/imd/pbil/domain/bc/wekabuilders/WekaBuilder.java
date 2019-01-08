package br.ufrn.imd.pbil.domain.bc.wekabuilders;

import br.ufrn.imd.pbil.domain.comm.wekabuilders.AdaBoostWekaBuilder;
import br.ufrn.imd.pbil.domain.comm.wekabuilders.BaggingWekaBuilder;
import br.ufrn.imd.pbil.domain.comm.wekabuilders.RandomCommitteeWekaBuilder;
import br.ufrn.imd.pbil.domain.comm.wekabuilders.RandomForestWekaBuilder;
import br.ufrn.imd.pbil.domain.comm.wekabuilders.StackingWekaBuilder;
import br.ufrn.imd.pbil.domain.comm.wekabuilders.VoteWekaBuilder;
import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import weka.classifiers.Classifier;

public class WekaBuilder {

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
			default :
				return null;
			}
}}