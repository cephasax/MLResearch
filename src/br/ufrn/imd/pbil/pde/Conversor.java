package br.ufrn.imd.pbil.pde;

import java.util.Set;

import br.ufrn.imd.pbil.annotations.ToFix;
import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.enums.ClassifierType;
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
	private static String IndividualName = null;
	
	public static Classifier buildMethod(String methodName) {
		
		Classifier classifier = null;
		
		switch(IndividualName) {

			case "weka.classifiers.tress.J48": {
				classifier = new J48();
				((J48)classifier).setOptions(options);
				break;
			}
				
			case "weka.classifiers.lazy.IBk": {
				classifier = new IBk();
				((J48)classifier).setOptions(options);
				break;
			}
				
			case "weka.classifiers.rules.DecisionTable": {
				classifier = new DecisionTable();
				((J48)classifier).setOptions(options);
				break;
			}
				
			case "weka.classifiers.bayes.BayesNet": {
				classifier = new BayesNet();
				((J48)classifier).setOptions(options);
				break;
			}

			case "weka.classifiers.bayes.NaiveBayes": {
				classifier = new NaiveBayes();
				((NaiveBayes)classifier).setOptions(options);
				break;
			}
				
			case "weka.classifiers.functions.MultilayerPerceptron": {
				classifier = new MultilayerPerceptron();
				((MultilayerPerceptron)classifier).setOptions(options);
				break;
			}
				
			case "weka.classifiers.functions.SMO": {
				classifier = new SMO();
				((SMO)classifier).setOptions(options);
				break;
			}
				
			case "weka.classifiers.lazy.Kstar": {
				classifier = new KStar();
				((KStar)classifier).setOptions(options);
				break;
			}
				
			case "weka.classifiers.trees.RandomTree": {
				classifier = new RandomTree();
				((RandomTree)classifier).setOptions(options);
				break;
			}
			
			case "RandomCommittee": {
				classifier = new RandomCommittee();
				((RandomCommittee)classifier).setOptions(options);
				break;
			}
				
			case "RandomForest": {
				classifier = new RandomForest();
				((RandomForest)classifier).setOptions(options);
				break;
			}
		
			case "AdaBoost": {
				classifier = new AdaBoostM1();
				((AdaBoostM1)classifier).setOptions(options);
				break;
			}
				
			case "Bagging": {
				classifier = new Bagging();
				((Bagging)classifier).setOptions(options);
				break;
			}
				
			case "Stacking": {
				classifier = new Stacking();
				((Stacking)classifier).setOptions(options);
				break;
			}
				
			case "Vote": {
				classifier = new Vote();
				((Vote)classifier).setOptions(options);
				break;
			}
		}		
	}
	
	private static String[] builOptios(PossibilityKeySet pks) {
		
		String[] options = builOptios(pks);
		float right = 0;
		float []accuracy = new float[5];
		
		Set<String> keys = pks.getKeyValuesPairs().keySet();
		int i =0;
		
		for(String key: keys) {
			if(pks.getKeyValuesPairs().get(key).equals("true") || pks.getKeyValuesPairs().get(key).equals("false")) {
				if(pks.getKeyValuesPairs().get(key).equals("true")) {
					options[i] = "-" + key;					
					i++;
				}
			}
			else if(key.equals("num")) {
				continue;
			}

			else {				
				options[i] = "-" + key;
				options[i+1] = pks.getKeyValuesPairs().get(key);
				i += 2;
			}
		}
		
		
		@ToFix
		Committee committee = null;
		
		if(pks.getBranchClassifiers().size() > 0) {
			committee = (Committee) classifier;
		}
		
		for (PossibilityKeySet pk : pks.getBranchClassifiers()) {
			options[i] = "-"+committee.getParameterClassifier();
			options[i+1] = pk.getKey();
			i+=2;
			/*for (String key: pk.getKeyValuesPairs().keySet()) {
				if(pk.getKeyValuesPairs().get(key).equals("true") 
						||pk.getKeyValuesPairs().get(key).equals("false")) {
					if(pk.getKeyValuesPairs().get(key).equals("true")) {
						options[i] = "-"+key;					
						i++;
					}
				}else {				
					options[i] = "-"+key;
					options[i+1] = pk.getKeyValuesPairs().get(key);
					i+=2;
				}
			}*/
		}
		return options;
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
		
		for(int j = 0; j < pks.getBranchClassifiers().size(); j++) {
			i += 2;
		}
		
		return i;
	}
}
