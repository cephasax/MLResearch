package br.ufrn.imd.pbil;

import java.util.Set;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.domain.Individual;
import br.ufrn.imd.pbil.enums.ClassifierType;
import br.ufrn.imd.pbil.pde.PossibilityKeySet;
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
import weka.core.converters.ConverterUtils.DataSource;

public class Conversor {
	private String base;
	DataSource data ;
	Instances dataset;
	Conversor(String base) throws Exception{
		this.base = base;
		data = new DataSource(this.base);
		dataset = data.getDataSet();
	}
	public double runSolution(Individual individual) throws Exception {
		dataset.setClassIndex(dataset.numAttributes() - 1);
		String[] options = builOptios(individual.getRootMethod());
		float right = 0;
		float []accuracy = new float[5];
		switch(individual.getName()) {
		//roda
		case "weka.classifiers.tress.J48":
			J48 j48 = new J48();
			j48.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				j48.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				right = 0;
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), j48.classifyInstance(test.get(j)))==0) {
						right++;
					}
				}
				accuracy[fold] = (right ) / test.size();
			}
			return (accuracy[0]+accuracy[1]+accuracy[2]+accuracy[3]+accuracy[4])/5;
		//roda
		case "weka.classifiers.lazy.IBk":
			IBk ibk = new IBk();
			ibk.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				ibk.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				right =0;
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), ibk.classifyInstance(test.get(j)))==0) {
						right++;
					}
				}
				accuracy[fold] = right / test.size();
			}
			return (accuracy[0]+accuracy[1]+accuracy[2]+accuracy[3]+accuracy[4])/5;
		case "weka.classifiers.rules.DecisionTable":
			DecisionTable dc = new DecisionTable();
			dc.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				dc.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				right =0;
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), dc.classifyInstance(test.get(j)))==0) {
						right++;
					}
				}
				accuracy[fold] = right / test.size();
			}
			return (accuracy[0]+accuracy[1]+accuracy[2]+accuracy[3]+accuracy[4])/5;
		case "weka.classifiers.bayes.BayesNet":
			BayesNet bn = new BayesNet();
			bn.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				bn.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				right =0;
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), bn.classifyInstance(test.get(j)))==0) {
						right++;
					}
				}
				accuracy[fold] = right / test.size();
			}
			return (accuracy[0]+accuracy[1]+accuracy[2]+accuracy[3]+accuracy[4])/5;
		//roda
		case "weka.classifiers.bayes.NaiveBayes":
			NaiveBayes nb = new NaiveBayes();
			nb.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				nb.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				right =0;
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), nb.classifyInstance(test.get(j)))==0) {
						right++;
					}
				}
				accuracy[fold] = right / test.size();
			}
			return (accuracy[0]+accuracy[1]+accuracy[2]+accuracy[3]+accuracy[4])/5;
	//roda	
		case "weka.classifiers.functions.MultilayerPerceptron":
			MultilayerPerceptron mlp = new MultilayerPerceptron();
			mlp.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				mlp.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				right = 0;
				for (int j = 0; j<test.size();j++) {
					double a =  test.get(j).classValue();
					double b = mlp.classifyInstance(test.get(j));
					if(Double.compare(a,b)==0) {
						right++;
					}
				}
				accuracy[fold] = (right / test.size());
			}
			return (accuracy[0]+accuracy[1]+accuracy[2]+accuracy[3]+accuracy[4])/5;
		case "weka.classifiers.functions.SMO":
			SMO smo = new SMO();
			smo.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				smo.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				right =0;
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), smo.classifyInstance(test.get(j)))==0) {
						right++;
					}
				}
				accuracy[fold]= right / test.size();
			}
			return (accuracy[0]+accuracy[1]+accuracy[2]+accuracy[3]+accuracy[4])/5;
		//roda
		case "weka.classifiers.lazy.Kstar":
			KStar kstar = new KStar();
			kstar.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				kstar.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				right =0;
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), kstar.classifyInstance(test.get(j)))==0) {
						right++;
					}
				}
				accuracy [fold]= right / test.size();
			}
			return (accuracy[0]+accuracy[1]+accuracy[2]+accuracy[3]+accuracy[4])/5;
		//roda
		case "weka.classifiers.trees.RandomTree":
			RandomTree rt = new RandomTree();
			rt.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				rt.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				right =0;
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), rt.classifyInstance(test.get(j)))==0) {
						right++;
					}
				}
				accuracy [fold]= (float) right / test.size();
			}
			return (accuracy[0]+accuracy[1]+accuracy[2]+accuracy[3]+accuracy[4])/5;
		case "RandomCommittee":
			RandomCommittee rc = new RandomCommittee();
			rc.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				rc.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				right =0;
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), rc.classifyInstance(test.get(j)))==0) {
						right++;
					}
				}
				accuracy [fold]= (float) right / test.size();
			}
			return (accuracy[0]+accuracy[1]+accuracy[2]+accuracy[3]+accuracy[4])/5;
		case "RandomForest":
			RandomForest rf = new RandomForest();
			rf.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				rf.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				right =0;
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), rf.classifyInstance(test.get(j)))==0) {
						right++;
					}
				}
				accuracy [fold]= (float) right / test.size();
			}
			return (accuracy[0]+accuracy[1]+accuracy[2]+accuracy[3]+accuracy[4])/5;
		case "AdaBoost":
			AdaBoostM1 ab = new AdaBoostM1();
			ab.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				ab.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				right =0;
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), ab.classifyInstance(test.get(j)))==0) {
						right++;
					}
				}
				accuracy [fold]= (float) right / test.size();
			}
			return (accuracy[0]+accuracy[1]+accuracy[2]+accuracy[3]+accuracy[4])/5;
		case "Bagging":
			Bagging bg = new Bagging();
			bg.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				bg.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				right =0;
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), bg.classifyInstance(test.get(j)))==0) {
						right++;
					}
				}
				accuracy [fold]= (float) right / test.size();
			}
			return (accuracy[0]+accuracy[1]+accuracy[2]+accuracy[3]+accuracy[4])/5;
		case "Stacking":
			Stacking st = new Stacking();
			st.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				st.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				right =0;
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), st.classifyInstance(test.get(j)))==0) {
						right++;
					}
				}
				accuracy [fold]= (float) right / test.size();
			}
			return (accuracy[0]+accuracy[1]+accuracy[2]+accuracy[3]+accuracy[4])/5;
		case "Vote":
			Vote vt = new Vote();
			vt.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				vt.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				right =0;
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), vt.classifyInstance(test.get(j)))==0) {
						right++;
					}
				}
				accuracy [fold]= (float) right / test.size();
			}
			return (accuracy[0]+accuracy[1]+accuracy[2]+accuracy[3]+accuracy[4])/5;
		default:
			return 66.6;
		}		
	}
	private String[] builOptios(Classifier classifier) {
		PossibilityKeySet pks = new PossibilityKeySet(classifier);
		String[] options = new String[sizeOfOptions(pks)];
		Set<String> keys = pks.getKeyValuesPairs().keySet();
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
		Committee committee = null;
		if(classifier.getClassifierType().equals(ClassifierType.COMMITTEE)) {
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
	private int sizeOfOptions(PossibilityKeySet pks) {
		Set<String> keys = pks.getKeyValuesPairs().keySet();
		int i =0;
		for (String key: keys) {
			if(pks.getKeyValuesPairs().get(key).equals("true")) {
				i++;
			}
			else if (key.equals("num")) {
				continue;
			}
			else if (!pks.getKeyValuesPairs().get(key).equals("false")) {
				i+=2;
			}
		}
		for (PossibilityKeySet pk : pks.getBranchClassifiers()) {
			i+=2;
		}
		return i;
	}
}