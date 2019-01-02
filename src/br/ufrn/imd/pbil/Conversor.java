package br.ufrn.imd.pbil;

import java.util.List;
import java.util.Set;

import br.ufrn.imd.pbil.domain.Individual;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.lazy.KStar;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomTree;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Conversor {
	private String base;
	Conversor(String base){
		this.base = base;
	}
	public double runSolution(Individual individual) throws Exception {
		DataSource data = new DataSource(this.base);
		Instances dataset = data.getDataSet();
		String[] options = builOptios(individual);
		int miss = 0;
		double accuracy = 0;
		switch(individual.getName()) {
		case "J48":
			J48 j48 = new J48();
			j48.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				j48.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), j48.classifyInstance(test.get(j)))!=0) {
						miss++;
					}
				}
				accuracy += miss / test.size();
			}
			accuracy = accuracy/5;
			
			return accuracy;
		case "IBK":
			IBk ibk = new IBk();
			ibk.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				ibk.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), ibk.classifyInstance(test.get(j)))!=0) {
						miss++;
					}
				}
				accuracy += miss / test.size();
			}
			accuracy = accuracy/5;
			return accuracy;
		case "DecisionTable":
			DecisionTable dc = new DecisionTable();
			dc.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				dc.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), dc.classifyInstance(test.get(j)))!=0) {
						miss++;
					}
				}
				accuracy += miss / test.size();
			}
			accuracy = accuracy/5;
			return accuracy;
		case "BayesNet":
			BayesNet bn = new BayesNet();
			bn.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				bn.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), bn.classifyInstance(test.get(j)))!=0) {
						miss++;
					}
				}
				accuracy += miss / test.size();
			}
			accuracy = accuracy/5;
			return accuracy;
		case "NaiveBayes":
			NaiveBayes nb = new NaiveBayes();
			nb.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				nb.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), nb.classifyInstance(test.get(j)))!=0) {
						miss++;
					}
				}
				accuracy += miss / test.size();
			}
			accuracy = accuracy/5;
			return accuracy;
		case "Mlp":
			MultilayerPerceptron mlp = new MultilayerPerceptron();
			mlp.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				mlp.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), mlp.classifyInstance(test.get(j)))!=0) {
						miss++;
					}
				}
				accuracy += miss / test.size();
			}
			accuracy = accuracy/5;
			return accuracy;
		case "Smo":
			SMO smo = new SMO();
			smo.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				smo.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), smo.classifyInstance(test.get(j)))!=0) {
						miss++;
					}
				}
				accuracy += miss / test.size();
			}
			accuracy = accuracy/5;
			return accuracy;
		case "Kstar":
			KStar kstar = new KStar();
			kstar.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				kstar.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), kstar.classifyInstance(test.get(j)))!=0) {
						miss++;
					}
				}
				accuracy += miss / test.size();
			}
			accuracy = accuracy/5;
			return accuracy;
		case "RandomTree":
			RandomTree rt = new RandomTree();
			rt.setOptions(options);
			for (int fold =0; fold<5;fold++) {
				rt.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), rt.classifyInstance(test.get(j)))!=0) {
						miss++;
					}
				}
				accuracy += miss / test.size();
			}
			accuracy = accuracy/5;
			return accuracy;
		default:
			return 66.6;
		}		
	}
	private String[] builOptios(Individual individual) {
		PossibilityKeySet pks = new PossibilityKeySet(individual.getRootMethod());
		String[] options = new String[pks.getKeyValuesPairs().size()*2];
		Set<String> keys = pks.getKeyValuesPairs().keySet();
		int i =0;
		for (String key: keys) {
		
			i++;
		}
		return options;
	}
}
