package br.ufrn.imd.pbil.douglas;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import br.ufrn.imd.pbil.pde.Solution;
import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class PbilWekaWorker {
	
	private String base;
	DataSource data ;
	Instances dataset;
	List<Classifier> classifiers;
	List<Solution> solutions;
	
	
	
	public PbilWekaWorker(String base) throws Exception{
		this.base = base;
		data = new DataSource(this.base);
		dataset = data.getDataSet();
		dataset.setClassIndex(dataset.numAttributes() - 1);
	}
	public void setSolutions(List<Solution> solutions) {
		this.solutions = solutions;
	}
	
	public void runSolution() throws Exception{
		
		float right = 0;
		double []accuracy = new double[5];
		
		for(int i =0; i<solutions.size(); i++) {
			Classifier c = classifiers.get(i);
			for (int fold =0; fold<5;fold++) {
				c.buildClassifier(dataset.trainCV(5, fold));
				final Instances test = dataset.testCV(5, fold);
				right = 0;
				for (int j = 0; j<test.size();j++) {
					if(Double.compare(test.get(j).classValue(), c.classifyInstance(test.get(j)))==0) {
						right++;
					}
				}
				accuracy[fold] = (right ) / test.size();
			}
			
			Solution solution = solutions.get(i);
			solution.setAccuracy( (accuracy[0]+accuracy[1]+accuracy[2]+accuracy[3]+accuracy[4]) / 5  );
			solutions.set(i, solution);
		}
		
		
	}
	
	public void convertSolutionsToWekaClassifiers() throws Exception {
		classifiers = new ArrayList<Classifier>();
		for(Solution solution: solutions){
			Classifier c = Conversor.buildMethod(solution);
			classifiers.add(c);
		}
	}
}
