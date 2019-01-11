package br.ufrn.imd.pbil.domain.bc.wekabuilders;

import br.ufrn.imd.pbil.domain.comm.wekabuilders.AdaBoostWekaBuilder;
import br.ufrn.imd.pbil.domain.comm.wekabuilders.BaggingWekaBuilder;
import br.ufrn.imd.pbil.domain.comm.wekabuilders.RandomCommitteeWekaBuilder;
import br.ufrn.imd.pbil.domain.comm.wekabuilders.RandomForestWekaBuilder;
import br.ufrn.imd.pbil.domain.comm.wekabuilders.StackingWekaBuilder;
import br.ufrn.imd.pbil.domain.comm.wekabuilders.VoteWekaBuilder;
import br.ufrn.imd.pbil.pde.Executor;
import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import weka.classifiers.Classifier;

public class WekaBuilder {

	public static Classifier buildClassifier(PossibilityKeySet pks) {

		Classifier c = null;
		
		Executor.enablePrint(false);
		try {

			switch (pks.getKey()) {
			case "weka.classifiers.trees.J48":
				c = J48WekaBuilder.buildForWeka(pks);
				break;
			case "weka.classifiers.trees.RandomTree":
				c = RandomTreeWekaBuilder.buildForWeka(pks);
				break;
			case "weka.classifiers.rules.DecisionTable":
				c = DecisionTableWekaBuilder.buildForWeka(pks);
				break;
			case "weka.classifiers.functions.MultilayerPerceptron":
				c = MlpWekaBuilder.buildForWeka(pks);
				break;
			case "weka.classifiers.functions.SMO":
				c = SmoWekaBuilder.buildForWeka(pks);
				break;
			case "weka.classifiers.lazy.IBk":
				c = IbkWekaBuilder.buildForWeka(pks);
				break;
			case "weka.classifiers.lazy.KStar":
				c = KstarWekaBuilder.buildForWeka(pks);
				break;
			case "weka.classifiers.bayes.NaiveBayes":
				c = NaiveBayesWekaBuilder.buildForWeka(pks);
				break;
			case "weka.classifiers.bayes.BayesNet":
				c = BayesNetWekaBuilder.buildForWeka(pks);
				break;
	
			case "Vote":
				c = VoteWekaBuilder.buildForWeka(pks);
				break;
			case "Stacking":
				c = StackingWekaBuilder.buildForWeka(pks);
				break;
			case "RandomCommittee":
				c = RandomCommitteeWekaBuilder.buildForWeka(pks);
				break;
			case "RandomForest":
				c = RandomForestWekaBuilder.buildForWeka(pks);
				break;
			case "AdaBoost":
				c = AdaBoostWekaBuilder.buildForWeka(pks);
				break;
			case "Bagging":
				c = BaggingWekaBuilder.buildForWeka(pks);
				break;
			}
		
		} catch (Exception e) {
			throw e;
		} finally {
			Executor.enablePrint(true);
		}
		
		return c;
	}
}