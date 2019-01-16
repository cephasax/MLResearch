package br.ufrn.imd.pbilautoens;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import br.ufrn.imd.pbilautoens.domain.bc.wekabuilders.WekaBuilder;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

public class PbilWekaWorker{
	
	private static final int maxNumThreads = 1;
	private Instances dataset;
	private ArrayList<Solution> correctSolutions;
	private ArrayList<Solution> solutions;
	private int fold;
	private int solutionTimeInSeconds;
	private int totalTime;
	private int populationSize;

	public PbilWekaWorker(Instances instances) throws Exception {
		correctSolutions = new ArrayList<Solution>();
		solutions = new ArrayList<Solution>();
		dataset = instances;
	}

	public void setSolutions(ArrayList<Solution> solutions) {
		this.solutions = solutions;
	}
	
	@SuppressWarnings("deprecation")
	public void runSolutions() {
		int success = 0;
		
		List<ExecutorThread> threads = new ArrayList<ExecutorThread>();
		
		List<ExecutorThread> running = new ArrayList<ExecutorThread>();
		
		for(Solution s: solutions) {
			ExecutorThread executorThread =  new ExecutorThread(s, fold, dataset);
			threads.add(executorThread);
		}
		
		while(!(threads.isEmpty() && running.isEmpty()) && success < populationSize ) {
			if(running.size()<maxNumThreads && !threads.isEmpty()) {
				ExecutorThread ex = threads.get(0);
				ex.start();
				ex.setInitTime(System.currentTimeMillis());
				running.add(ex);
				threads.remove(0);
			}
			
			Iterator<ExecutorThread> it = running.iterator();
			while(it.hasNext() ) {
				ExecutorThread e = it.next();
				boolean val = System.currentTimeMillis() - e.getInitTime() >= solutionTimeInSeconds * 1000 && !e.isFinish();
				if(val) {
					e.getSolution().setGoodSolution(false);
					e.stop();
					it.remove();
					//System.out.println("Error for " + e.getSolution());
				}
				else if(e.isFinish()){
					it.remove();
					if(e.isValid()) {
						success++;
						correctSolutions.add(e.getSolution());
					}
					//System.out.println("Success for " + e.getSolution());
				}
			}
			
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
		}
		for(int j =0; j<running.size();j++) {
			running.get(j).stop();
		}
	}

	public void convertSolutionsToWekaClassifiers() throws Exception {
		for (int i = 0; i < solutions.size(); i++) {
			Classifier c = WekaBuilder.buildClassifier(solutions.get(i).getPossibilityKeySet());
			solutions.get(i).setClassifier(c);
		}
	}
	
	public List<Solution> getCorrectSolutions() {
		return correctSolutions;
	}

	public void setCorrectSolutions(ArrayList<Solution> correctSolutions) {
		this.correctSolutions = correctSolutions;
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
		return solutionTimeInSeconds;
	}

	public void setSolutionTime(int solutionTime) {
		this.solutionTimeInSeconds = solutionTime;
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

	public static void printErrorMessage(Exception e, PossibilityKeySet pks) {
		System.out.println("ERROR on " + pks.toString());
	}
	
	public String bestResultWekaFormatAsString(Solution s) {
		Classifier c = WekaBuilder.buildClassifier(s.getPossibilityKeySet());
		Evaluation eval = null;
		try {
			eval = new Evaluation(dataset);
			eval.crossValidateModel(c, dataset, fold, new Random(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = eval.toSummaryString();
		return result;
	}
	
	public void clearSolutionLists() {
		solutions = new ArrayList<Solution>();
		correctSolutions = new ArrayList<Solution>();
	}
	
}
