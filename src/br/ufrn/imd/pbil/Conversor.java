package br.ufrn.imd.pbil;

import java.util.Set;

import br.ufrn.imd.pbil.domain.Individual;
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
	DataSource data ;
	Instances dataset;
	
	public Conversor(String base) throws Exception{
		this.base = base;
		data = new DataSource(this.base);
		dataset = data.getDataSet();
	}
	
	public double runSolution(Individual individual) throws Exception {
		dataset.setClassIndex(dataset.numAttributes() - 1);
		String[] options = builOptios(individual);
		float right = 0;
		float []accuracy = new float[5];
		switch(individual.getName()) {
		//roda
		case "J48":
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
		case "IBK":
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
		case "DecisionTable":
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
		case "BayesNet":
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
		case "NaiveBayes":
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
		case "MLP":
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
		case "Smo":
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
		case "Kstar":
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
		case "RandomTree":
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
		default:
			return 66.6;
		}		
	}
	private String[] builOptios(Individual individual) {
		PossibilityKeySet pks = new PossibilityKeySet(individual.getRootMethod());
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
			}else {
//				if(pks.getKeyValuesPairs().get(key).equals("BestFirst")) {
//					options[i] = "-"+key;
//					options[i+1] = ""+pks.getKeyValuesPairs().get(key);
//					options[i+2] = "-D";
//					options[i+3] = "1";
//					options[i+4] = "-N";
//					options[i+5] = "5";
//					i=+6;
//				}else if(pks.getKeyValuesPairs().get(key).equals("GreedyStepWise")){
//					options[i] = "-"+key;
//					options[i+1] = ""+pks.getKeyValuesPairs().get(key);
//					options[i+2] = "-T";
//					options[i+3] = "-1.7976931348623157E308";
//					options[i+4] = "-N";
//					options[i+5] = "1";
//					options[i+6] = "-num-slots";
//					options[i+7] = "1";
//					i+=8;
//				}else {					
					options[i] = "-"+key;
					options[i+1] = pks.getKeyValuesPairs().get(key);
					i+=2;
				
			}
		}
		return options;
	}
	private int sizeOfOptions(PossibilityKeySet pks) {
		Set<String> keys = pks.getKeyValuesPairs().keySet();
		int i =0;
		for (String key: keys) {
			if(pks.getKeyValuesPairs().get(key).equals("BestFirst")) {
				i+=6;
			}
			else if(pks.getKeyValuesPairs().get(key).equals("GreedyStepWise")){
				i+=8;
			}
			else if(pks.getKeyValuesPairs().get(key).equals("true")) {
				i++;
			}
			else if (!pks.getKeyValuesPairs().get(key).equals("false")) {
				i+=2;
			}
		}
		return i;
	}
}
