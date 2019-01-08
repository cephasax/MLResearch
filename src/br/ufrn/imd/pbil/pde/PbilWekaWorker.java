package br.ufrn.imd.pbil.pde;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pbil.domain.bc.wekabuilders.WekaBuilder;
import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class PbilWekaWorker{

	private String base;
	private DataSource data;
	private Instances dataset;
	private ArrayList<Solution> correctSolutions;
	private ArrayList<Solution> solutions;
	private int fold;
	private int solutionTime;
	private int totalTime;
	private int populationSize;

	public PbilWekaWorker(String base) throws Exception {
		correctSolutions = new ArrayList<Solution>();
		solutions = new ArrayList<Solution>();

		this.base = base;
		data = new DataSource(this.base);
		dataset = data.getDataSet();
		dataset.setClassIndex(dataset.numAttributes() - 1);
	}

	public void setSolutions(ArrayList<Solution> solutions) {
		this.solutions = solutions;
	}
	
	public void runSolutions(){
		for(Solution s: correctSolutions) {
			try {
				s.setAccuracy(Executor.runSolution(dataset, s.getClassifier(), fold));
				System.out.println(s.getAccuracy() + " : " +s.getPossibilityKeySet().toString());
			} catch (Exception e) {
				printErrorMessage(e, s.getPossibilityKeySet());
			}
		}
	}

	public void convertSolutionsToWekaClassifiers() throws Exception {
		for (int i = 0; i < solutions.size(); i++) {
			Classifier c = WekaBuilder.buildClassifier(solutions.get(i).getPossibilityKeySet());
		}
	}
	
	public List<Solution> getCorrectSolutions() {
		return correctSolutions;
	}

	public void setCorrectSolutions(ArrayList<Solution> correctSolutions) {
		this.correctSolutions = correctSolutions;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public DataSource getData() {
		return data;
	}

	public void setData(DataSource data) {
		this.data = data;
	}

	public Instances getDataset() {
		return dataset;
	}

	public void setDataset(Instances dataset) {
		this.dataset = dataset;
	}

	public int getFold() {
		return fold;
	}

	public void setFold(int fold) {
		this.fold = fold;
	}

	public int getSolutionTime() {
		return solutionTime;
	}

	public void setSolutionTime(int solutionTime) {
		this.solutionTime = solutionTime;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public List<Solution> getSolutions() {
		return solutions;
	}

	private void printErrorMessage(Exception e, PossibilityKeySet pks) {
		System.out.println("     -----     ");
		System.out.println(pks.toString());
		System.out.println(e.getMessage());
		System.out.println("     -----     ");
	}
	
}
