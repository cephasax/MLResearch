package br.ufrn.imd.pbil.pde;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import br.ufrn.imd.pbil.domain.Factory;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;
import br.ufrn.imd.pbil.fileManipulation.PbilOutputWriter;

public class Pbil {
	
	private static int populationSize = 10;									
	private static int maxMinutes = 15;											
	private static int generations = 5;										
																			
	private static int numFolds = 10;																				
	private static int maxSecondsPerSolution = (maxMinutes * 60) / 12; 	
	private static String log = "PBIL-";									
	
	public static float learningRate = (float) 0.5;
	
	private static Factory f;		
	private static ArrayList<Solution> population;
	private static ArrayList<Solution> auxPopulation;
	private static int updateReason = 2;					// best solutions size to improve pv (2 = 1/2, n = 1/n, where 1=population size)
	
	private static Solution bestSolution;
	private static ArrayList<String> dataSets;
	private static PbilOutputWriter pow;
	private static PbilWekaWorker pww;
	private static String baseDatasetPath;
	
	private static String actualDataset;
		
	public static void main(String[] args) throws Exception {

		buildDatasetPaths();
		
		for(String dataset: dataSets) {
			actualDataset = dataset;
			buildVariables();
			buildPbilWorker(dataset);
			
			for(int j = 1; j <= generations; j++) {
				
				outputStuffAboutRunning(dataset, j);
				
				buildSolutions();
				
				logPopulation();
				
					buildAndRunWekaSolutions();

				run();
				orderSolutions();
				
				logAfterOrdering();
				
				keepBestSolution();
				
				darwinLaw();
				
				logAuxPopulation();
				
				updateProbabilities();
				
				logBestSolution();
				
				outputSave();
			}	
		}

	}

	private static void run() {
		pww.runSolutions();
		population = (ArrayList<Solution>) pww.getCorrectSolutions();
		
	}

	public static void buildVariables() throws InvalidParameterTypeException{
		
		f = new Factory();
		f.setLearningRate(learningRate);
		baseDatasetPath = new String("datasets/");
		population = new ArrayList<Solution>();
		auxPopulation = new ArrayList<Solution>();
		bestSolution = new Solution();
		
		try {
			pow = new PbilOutputWriter("results/" + log + actualDataset);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void buildPbilWorker(String dataset) {
		try {
			pww = new PbilWekaWorker(baseDatasetPath + dataset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pww.setFold(numFolds);
		pww.setSolutionTime(maxSecondsPerSolution);
		pww.setTotalTime(maxMinutes);
		pww.setPopulationSize(populationSize);
	}
	
	public static void buildDatasetPaths(){
		dataSets = new ArrayList<String>();
		dataSets.add("x.arff");
		dataSets.add("iris.arff");
	}
	
	public static void buildSolutions() {
		int extraSolutions = (int) populationSize / 2;
		int total = populationSize + extraSolutions;
		PossibilityKeySet s;
		
		for(int i = 0; i < total;i++) {
			s = f.buildSolutionFromWeightedDraw();
			population.add(new Solution(s));
		}
	}
	
	public static void buildAndRunWekaSolutions() throws Exception {
		pww.setSolutions(population);
		pww.convertSolutionsToWekaClassifiers();
		
	}
	
	public static void outputStuffAboutRunning(String dataset, int generation) {
		try {
			pow.logDetailsAboutStep(dataset, generation);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void logPopulation() {
		pow.addContentline(" >>> Population \n ");
		
		for(Solution s: population) {
			pow.logSolution(s);
		}

		try {
			pow.writeInFile();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static void outputSave() {
		try {
			pow.saveAndClose();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void orderSolutions() {
		Collections.sort(population, Solution.AccuracyComparator);
	}

	public static void keepBestSolution() {
		if(bestSolution.getAccuracy() <= population.get(0).getAccuracy()) {
			bestSolution = population.get(0);
		}
	}
	
	public static void darwinLaw() {
		for(int i = 1; i <= ((int)(populationSize/ updateReason));i++) {
			auxPopulation.add(population.get(i));
		}
		population.clear();
	}
	
	private static void updateProbabilities() {
		for(Solution s: auxPopulation) {
			f.updatePossibilities(s.getPossibilityKeySet());
		}
		auxPopulation.clear();
	}

	private static void logAuxPopulation() {
		pow.addContentline("\n >>> Best Solutions stored and used to increase pvs \n ");
		for(Solution s: auxPopulation) {
			pow.logSolution(s);
		}

		try {
			pow.writeInFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void logBestSolution() {
		pow.addContentline("\n >>> Best Solution for " + actualDataset + " - accoding accuracy \n ");
		pow.logSolutionAccuracyFirst(bestSolution);

		try {
			pow.writeInFile();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static void logAfterOrdering() {
		pow.addContentline("\n >>> Ordered Population - according accuracy \n ");
		
		for(int i = 0; i < population.size();i++) {
			pow.logSolutionAccuracyFirst(population.get(i));
		}

		try {
			pow.writeInFile();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
