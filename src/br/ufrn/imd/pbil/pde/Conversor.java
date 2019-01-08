package br.ufrn.imd.pbil.pde;

import br.ufrn.imd.pbil.domain.bc.wekabuilders.DecisionTableWekaBuilder;
import jdk.nashorn.internal.AssertsEnabled;
import weka.attributeSelection.ASSearch;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.lazy.KStar;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.Bagging;
import weka.classifiers.meta.RandomCommittee;
import weka.classifiers.meta.Stacking;
import weka.classifiers.meta.Vote;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomTree;
import weka.core.SelectedTag;
import weka.core.Tag;

public class Conversor{
	
	private static String[] options = {};
	private static String individualName = null;
	
	private J48 j48;
	private KStar kstar;
	private MultilayerPerceptron mlp;
	private SMO smo;
	private DecisionTable dt;
	private NaiveBayes nb;
	private RandomTree rt;
	private BayesNet bn;
	private IBk ibk;
	private RandomCommittee rc;
	private RandomForest rf;
	private Vote vote;
	private Stacking stc;
	private AdaBoostM1 ab;
	private Bagging bg;
	
	public Classifier buildMethod(Solution solution) throws Exception  {
		PossibilityKeySet pks = solution.getPossibilityKeySet();
		switch(individualName) {

			case "weka.classifiers.trees.J48": {
				j48 = new J48();
				j48.setOptions(options);
				return j48;
			}
				
			case "weka.classifiers.lazy.IBk": {
				ibk = new IBk();
				ibk.setOptions(options);
				return ibk;
			}
				
			case "weka.classifiers.rules.DecisionTable": {
				return DecisionTableWekaBuilder.buildWekaDecisionTable(pks);
			}
				
			case "weka.classifiers.bayes.BayesNet": {
				bn = new BayesNet();
				bn.setOptions(options);
				return bn;
			}

			case "weka.classifiers.bayes.NaiveBayes": {
				nb = new NaiveBayes();
				nb.setOptions(options);
				return nb;
			}
				
			case "weka.classifiers.functions.MultilayerPerceptron": {
				mlp = new MultilayerPerceptron();
				mlp.setOptions(options);
				return mlp;
			}
				
			case "weka.classifiers.functions.SMO": {
				smo = new SMO();
				smo.setOptions(options);
				return smo;
			}
				
			case "weka.classifiers.lazy.KStar": {
				kstar = new KStar();
				kstar.setOptions(options);
				return kstar;
			}
				
			case "weka.classifiers.trees.RandomTree": {
				rt = new RandomTree();
				rt.setOptions(options);
				return rt;
			}
			
			case "RandomCommittee": {
				rc = new RandomCommittee();
				rc.setOptions(options);
				return rc;
			}
				
			case "RandomForest": {
				rf = new RandomForest();
				rf.setOptions(options);
				return rf;
			}
		
			case "AdaBoost": {
				ab = new AdaBoostM1();
				ab.setOptions(options);
				return ab;
			}
				
			case "Bagging": {
				bg  = new Bagging();
				bg.setOptions(options);
				return bg;
			}
				
			case "Stacking": {
				stc = new Stacking();
				stc.setOptions(options);
				return stc;
			}
				
			case "Vote": {
				vote = new Vote();
				vote.setOptions(options);
				return vote;
			}
			default:
				return null;
		}		
	}

	
}
