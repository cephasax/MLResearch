package br.ufrn.imd.pbilautoens;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import br.ufrn.imd.pbilautoens.fileManipulation.PbilOutputWriter;
import weka.core.Instances;

public class Pbil {

	private PbilRandom pbilRandom;
	private int populationSize = 50;
	private int maxMinutes = 15;
	private int generations = 20;

	private int numFolds = 10;
	private int maxSecondsPerSolution = (maxMinutes * 60) / 12;
	private String log = "PbilAutoEns_";

	public float learningRate = (float) 0.5;

	private Factory f;
	private ArrayList<Solution> population;
	private ArrayList<Solution> auxPopulation;
	private int updateReason = 2; // best solutions size to improve pv (2 = 1/2, n = 1/n, where 1=population size)

	private Solution bestSolution;

	private Instances instances;
	private PbilOutputWriter pow;
	private PbilWekaWorker pww;

	private int performedSteps;

	private String actualDataset;

	public void run() throws Exception {

		// ---- BuildVariables();

		f = new Factory(pbilRandom);
		f.setLearningRate(learningRate);
		population = new ArrayList<Solution>();
		auxPopulation = new ArrayList<Solution>();
		bestSolution = new Solution();
		
		actualDataset = instances.relationName();
		
		try {
			pow = new PbilOutputWriter("resources/results/" + log + actualDataset);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ---- BuildPbilWorker(dataset);
		try {
			pww = new PbilWekaWorker(instances);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pww.setFold(numFolds);
		pww.setSolutionTime(maxSecondsPerSolution);
		pww.setTotalTime(maxMinutes);
		pww.setPopulationSize(populationSize);

		//prevent error with empty bestSolution
		bestSolution = new Solution();
		
		
		//TERMINAL - DETAILS ABOUT DATASET
		outputDatasetInfo();
		
		for (performedSteps = 0; performedSteps < generations; performedSteps++) {
			
			// ---- OutputStuffAboutRunning(dataset, j);
			try {
				pow.logDetailsAboutStep(instances.relationName(), performedSteps);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// ---- BuildSolutions();
			int extraSolutions = populationSize;
			int total = populationSize + extraSolutions;
			for (int i = 0; i < total; i++) {
				PossibilityKeySet s = f.buildSolutionFromWeightedDraw();
				population.add(new Solution(s));
			}
			//TERMINAL - DETAILS ABOUT POPULATION
			outPutGenerationInfo();

			// ---- logPopulation();
			pow.addContentline(" --->>>> Population <<<<---\n");
			for (Solution s : population) {
				pow.logSolution(s);
			}
			try {
				pow.writeInFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// ---- buildAndRunWekaSolutions();
			pww.setSolutions(population);
			pww.convertSolutionsToWekaClassifiers();
			
			//TERMINAL - RUNNING INFO
			outputRunningInfo();
			
			// ---- run();
			pww.runSolutions();
			population = (ArrayList<Solution>) pww.getCorrectSolutions();

			// ---- orderSolutions();
			Collections.sort(population, Solution.meanErrorComparator);
			
			//TERMINAL - ORDERING INFO
			outputInfo("\nORDERING SOLUTIONS ACCORDING FITNESS \n");
			
			// ---- logBadSolutions
			// ---- TODO

			// ---- logAfterOrdering();
			pow.addContentline("\n --->>>> Ordered Population - according mean Error <<<<---\n");
			for (int i = 0; i < population.size(); i++) {
				pow.logSolutionAccuracyFirst(population.get(i));
			}
			try {
				pow.writeInFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// ---- keepBestSolution();
			// !population.isEmpty(): case in that all evaluated solves are invalid
			if (!population.isEmpty() && population.get(0).getMeanError() < bestSolution.getMeanError()) {
				bestSolution = population.get(0);
			}
			
			//TERMINAL - ORDERING INFO
			outputInfo("GETTING ONLY BEST SOLUTION ACCORDING FITNESS \n");
			
			// ---- darwinLaw();
			for (int i = 0; i < Math.min(((int) (populationSize / updateReason)), population.size()); i++) {
				auxPopulation.add(population.get(i));
			}
			population.clear();

			//TERMINAL - DARWIN LAW
			outputInfo("APPLYING THE DARWIN'S LAW \n");
			
			// ---- logAuxPopulation();
			pow.addContentline("\n --->>>> Best Solutions kept and used to increase pvs <<<<---\n");
			for (Solution s : auxPopulation) {
				pow.logSolution(s);
			}
			try {
				pow.writeInFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// ---- updateProbabilities();
			for (Solution s : auxPopulation) {
				f.updatePossibilities(s.getPossibilityKeySet());
			}
			auxPopulation.clear();
			
			//TERMINAL - UPDATE PVs
			outputInfo("UPDATING POSSIBILITIES WEIGHTS \n");
			

			// ---- logBestSolution();
			pow.addContentline("\n --->>>> Best Solution for " + actualDataset + " - according mean error <<<<---\n");
			pow.logSolutionAccuracyFirst(bestSolution);
			try {
				pow.writeInFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//TERMINAL - BEST SOLUTION
			outputBestSolutionInfo();
			
			// ---- outputSave();
			try {
				pow.saveAndClose();
			} catch (IOException e) {
				e.printStackTrace();
			}
			pww.clearSolutionLists();
		}
		
		// ---- log PVs
		pow.addContentline("\n --->>>> Final Probabilities dataStructure for" + actualDataset);
		pow.addContentline(f.getFirstLevel().possibilityAsGsonString());
		pow.addContentline(f.getBaseClassifierPossibilities().possibilityAsGsonString());
		pow.addContentline(f.getCommitteePossibilities().possibilityAsGsonString());
		pow.addContentline(f.getBranchClassifierPossibilities().possibilityAsGsonString());
		
		try {
			pow.writeInFile();
			pow.saveAndClose();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getPerformedSteps() {
		return performedSteps;
	}

	public void setInstances(Instances instances) {
		this.instances = instances;
	}

	public Solution getBestSolution() {
		return bestSolution;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public void setGenerations(int generations) {
		this.generations = generations;
	}

	public void setNumFolds(int numFolds) {
		this.numFolds = numFolds;
	}

	public void setMaxSecondsPerSolution(int maxSecondsPerSolution) {
		this.maxSecondsPerSolution = maxSecondsPerSolution;
	}

	public void setLearningRate(float learningRate) {
		this.learningRate = learningRate;
	}

	public void setUpdateReason(int updateReason) {
		this.updateReason = updateReason;
	}

	public void setRandomSeed(int seed) {
		this.pbilRandom = new PbilRandom(seed);
	}

	public void logInfo(String string) {
		pow.addContentline(string);
		pow.write();
		try {
			pow.saveAndClose();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void outputInfo(String ... strings ) {
		for(String s: strings) {
			pow.appendContent(s);
		}
		pow.write();
	}
	
	public void outputDatasetInfo() {
		pow.outputDatasetInfo(actualDataset);
	}
	
	public void outPutGenerationInfo() {
		outputInfo("POPULATION", String.valueOf(performedSteps), "BUILT", "\n");
	}
	
	public void outputRunningInfo() {
		outputInfo("RUNING POPULATION", String.valueOf(performedSteps), "OVER DATASET", "\n");
	}
	
	public void outputBestSolutionInfo() {
		pow.outputBestSolutionInfo(performedSteps, bestSolution.getPossibilityKeySet().toString());
	}
	
	public void logBadSolutions() {
		/*
		 * String badSolutionsLogPath = "results/badSolutions/";; FileOutputWriter
		 * badSolutionsLogger = new FileOutputWriter(badSolutionsLogPath +
		 * "badSolutions_"+ dataset); badSolutionsLogger.addContentline(
		 * "------------------------------------------------------------------------");
		 * badSolutionsLogger.addContentline("\n --->>>> Bad Solutions at generation: "
		 * + j); badSolutionsLogger.addContentline(
		 * "------------------------------------------------------------------------");
		 * for(Solution s: pww.getSolutions()) { if(!s.isGoodSolution()) {
		 * badSolutionsLogger.addContentline(s.getPossibilityKeySet().toString()); } }
		 * badSolutionsLogger.writeInFile(); badSolutionsLogger.saveAndClose();
		 */
	}

}
