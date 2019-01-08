package br.ufrn.imd.pbil.domain.bc.wekabuilders;

import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.GreedyStepwise;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.net.search.local.HillClimber;
import weka.classifiers.bayes.net.search.local.K2;
import weka.classifiers.bayes.net.search.local.LAGDHillClimber;
import weka.classifiers.bayes.net.search.local.SimulatedAnnealing;
import weka.classifiers.bayes.net.search.local.TAN;
import weka.classifiers.bayes.net.search.local.TabuSearch;
import weka.classifiers.rules.DecisionTable;
import weka.core.SelectedTag;
import weka.core.Tag;

public class BayesNetWekaBuilder {

	public static BayesNet buildWekaBayesNet(PossibilityKeySet pks) {
		BayesNet bn = new BayesNet();
		
		String sh = pks.getKeyValuesPairs().get("Q");
		bn.setUseADTree(Boolean.parseBoolean(pks.getKeyValuesPairs().get("D")));
		if(sh.equals("weka.classifiers.bayes.net.search.local.K2")) {
			bn.setSearchAlgorithm(new K2());
		}
		
		else if (sh.equals("weka.classifiers.bayes.net.search.local.HillClimber")) {
			bn.setSearchAlgorithm(new HillClimber());
		}
		
		else if (sh.equals("weka.classifiers.bayes.net.search.local.LAGDHillClimber")) {
			bn.setSearchAlgorithm(new LAGDHillClimber());
		}
		
		else if (sh.equals("weka.classifiers.bayes.net.search.local.SimulatedAnnealing")) {
			bn.setSearchAlgorithm(new SimulatedAnnealing());
		}
		
		else if (sh.equals("weka.classifiers.bayes.net.search.local.TabuSearch")) {
			bn.setSearchAlgorithm(new TabuSearch());
		}
		
		else if (sh.equals("weka.classifiers.bayes.net.search.local.TAN")) {
			bn.setSearchAlgorithm(new TAN());
		}
		
		else {
			bn.setSearchAlgorithm(new K2());
		}
		
		return bn;
	}
	
}
