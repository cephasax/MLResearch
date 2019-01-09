package br.ufrn.imd.pbil.pde;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.Classifier;
import weka.core.Instances;

public class Executor {
	
	public static List<Float> runSolution (Instances dataset, Classifier classifier, int numFolds) throws Exception{
		List<Float>foldMinError = new ArrayList<Float>();
		float wrongClassified = 0;
		for (int fold =0; fold<numFolds;fold++) {
			classifier.buildClassifier(dataset.trainCV(numFolds, fold));
			Instances test = dataset.testCV(numFolds, fold);
			for (int i =0; i< test.size();i++) {
				if(Double.compare( test.get(i).classValue() , classifier.classifyInstance(test.get(i)))!=0) {
					wrongClassified++;
				}
			}
			foldMinError.add(wrongClassified/test.size());
			wrongClassified =0;
		}
		
		return foldMinError;
	}
}
