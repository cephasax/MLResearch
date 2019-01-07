package br.ufrn.imd.pbil.douglas;

import java.util.Set;

import br.ufrn.imd.pbil.annotations.ToFix;
import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.enums.ClassifierType;
import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import br.ufrn.imd.pbil.pde.Solution;
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
import weka.core.Instances;

public class Conversor{
	
	private static String[] options = {};
	private static String individualName = null;
	
	private static J48 j48;
	private static KStar kstar;
	private static MultilayerPerceptron mlp;
	private static SMO smo;
	private static DecisionTable dc;
	private static NaiveBayes nb;
	private static RandomTree rt;
	private static BayesNet bn;
	private static IBk ibk;
	private static RandomCommittee rc;
	private static RandomForest rf;
	private static Vote vote;
	private static Stacking stc;
	private static AdaBoostM1 ab;
	private static Bagging bg;
	
	public static Classifier buildMethod(Solution solution) throws Exception  {
		
		options = buildOptions(solution.getPossibilityKeySet());
		individualName = solution.getPossibilityKeySet().getKey();
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
				dc = new DecisionTable();
				dc.setOptions(options);
				return dc;
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
	

	
	private static int sizeOfOptions(PossibilityKeySet pks) {
		
		Set<String> keys = pks.getKeyValuesPairs().keySet();
		int i =0;
		
		for(String key: keys) {
			if(pks.getKeyValuesPairs().get(key).equals("true")) {
				i++;
			}
			else if(key.equals("num")) {
				continue;
			}
			else if(!pks.getKeyValuesPairs().get(key).equals("false")) {
				i += 2;
			}
		}
		
		for (PossibilityKeySet pk : pks.getBranchClassifiers()) {
			i+=2;
			for (String key: pk.getKeyValuesPairs().keySet()) {
				if(pk.getKeyValuesPairs().get(key).equals("true") 
						||pk.getKeyValuesPairs().get(key).equals("false")) {
					if(pk.getKeyValuesPairs().get(key).equals("true")) {					
						i++;
					}
				}
				else if (key.equals("num")) {
					continue;
				}

				else {				
					i+=2;
				}
			}
		}
		
		return i;
	}
	
	
	
	private static String[] buildOptions(PossibilityKeySet pks) {
		String[] options = new String[sizeOfOptions(pks)];
		Set<String> keys = pks.getKeyValuesPairs().keySet();
		String branchClassifierParameter = getBranckClassifierParameter(pks.getKey());
		int i =0;
		for (String key: keys) {
			if(pks.getKeyValuesPairs().get(key).equals("true") 
					||pks.getKeyValuesPairs().get(key).equals("false")) {
				if(pks.getKeyValuesPairs().get(key).equals("true")) {
					options[i] = "-"+key;					
					i++;
				}
			}
			else if (key.equals("num")) {
				continue;
			}

			else {				
				options[i] = "-"+key;
				options[i+1] = pks.getKeyValuesPairs().get(key);
				i+=2;
			}
		}
		//for each branch classifier build its options
		for (PossibilityKeySet pk : pks.getBranchClassifiers()) {
			options[i] = "-"+branchClassifierParameter;
			options[i+1] = pk.getKey();
			i+=2;
			for (String key: pk.getKeyValuesPairs().keySet()) {
				if(pk.getKeyValuesPairs().get(key).equals("true") 
						||pk.getKeyValuesPairs().get(key).equals("false")) {
					if(pk.getKeyValuesPairs().get(key).equals("true")) {
						options[i] = "-"+key;					
						i++;
					}
				}
				else if (key.equals("num")) {
					continue;
				}

				else {				
					options[i] = "-"+key;
					options[i+1] = pk.getKeyValuesPairs().get(key);
					i+=2;
				}
			}
		}
		return options;
	}



	private static String getBranckClassifierParameter(String key) {
		if(key.equals("Stacking")) {
			return "M";
		}
		else if (key.equals("Vote")) {
			return "B";
		}
		return "W";
	}
}
