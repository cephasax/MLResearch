package br.ufrn.imd.pbil.pde;

import weka.classifiers.Classifier;
import weka.core.Instances;

public class Executor {
	
	public static float runSolution (Instances dataset, Classifier classifier, int numFolds) throws Exception{
		float correctClassified = 0;
		float accuracySum=0;
		for (int fold =0; fold<numFolds;fold++) {
			classifier.buildClassifier(dataset.trainCV(numFolds, fold));
			Instances test = dataset.testCV(numFolds, fold);
			for (int i =0; i< test.size();i++) {
				if(Double.compare( test.get(i).classValue() , classifier.classifyInstance(test.get(i)))==0) {
					correctClassified++;
				}
			}
			accuracySum	+= correctClassified/test.size();
			correctClassified =0;
		}
		return accuracySum /numFolds;
	}
}
