package br.ufrn.imd.pbil.pde;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import br.ufrn.imd.pbil.domain.Factory;
import br.ufrn.imd.pbil.fileManipulation.PbilOutputWriter;

public class Pbil {
	
	private static int populationSize = 50;									
	private static int maxMinutes = 15;											
	private static int generations = 20;										
																			
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
		
		baseDatasetPath = new String("datasets/");
		
		//---- BuildDatasetPaths();
		dataSets = new ArrayList<String>();
		
		dataSets.add("diabetes.arff");
		dataSets.add("glass.arff");
		dataSets.add("ionospehre.arff");
		dataSets.add("iris.arff");
		dataSets.add("labor.arff");
		dataSets.add("segment-challenge.arff");
		dataSets.add("segment-test.arff");
		dataSets.add("soybean.arff");
		dataSets.add("weather.arff");
		dataSets.add("weather.nominal.arff");
		
		for(String dataset: dataSets) {
			
			actualDataset = dataset;
			
			//---- BuildVariables();
		
			f = new Factory();
			f.setLearningRate(learningRate);
			population = new ArrayList<Solution>();
			auxPopulation = new ArrayList<Solution>();
			bestSolution = new Solution();
			try {
				pow = new PbilOutputWriter("results/" + log + actualDataset);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			//---- BuildPbilWorker(dataset);
			try {
				pww = new PbilWekaWorker(baseDatasetPath + dataset);
			} catch (Exception e) {
				e.printStackTrace();
			}
			pww.setFold(numFolds);
			pww.setSolutionTime(maxSecondsPerSolution);
			pww.setTotalTime(maxMinutes);
			pww.setPopulationSize(populationSize);
			
			for(int j = 1; j <= generations; j++) {
				
				//---- OutputStuffAboutRunning(dataset, j);
				try {
					pow.logDetailsAboutStep(dataset, j);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//---- BuildSolutions();
				int extraSolutions = populationSize;
				int total = populationSize + extraSolutions;
				for(int i = 0; i < total;i++) {
					PossibilityKeySet s = f.buildSolutionFromWeightedDraw();
					population.add(new Solution(s));
				}
								
				//---- logPopulation();
				pow.addContentline(" --->>>> Population <<<<---\n");
				for(Solution s: population) {
					pow.logSolution(s);
				}
				try {
					pow.writeInFile();
				} catch (IOException e) {
					e.printStackTrace();
				}		
				
				//---- buildAndRunWekaSolutions();
				pww.setSolutions(population);
				pww.convertSolutionsToWekaClassifiers();
				
				//---- run();
				pww.runSolutions();
				population = (ArrayList<Solution>) pww.getCorrectSolutions();
								
				//---- orderSolutions();
				Collections.sort(population, Solution.meanErrorComparator);
				
				//---- logBadSolutions 
				//---- to do
				
				//---- logAfterOrdering();
				pow.addContentline("\n --->>>> Ordered Population - according mean Error <<<<---\n");
				for(int i = 0; i < population.size();i++) {
					pow.logSolutionAccuracyFirst(population.get(i));
				}
				try {
					pow.writeInFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//---- keepBestSolution();
				if(bestSolution.getAccuracy() <= population.get(0).getAccuracy()) {
					bestSolution = population.get(0);
				}
				
				//---- darwinLaw();
				for(int i = 1; i <= ((int)(populationSize/ updateReason));i++) {
					auxPopulation.add(population.get(i));
				}
				population.clear();
				
				//---- logAuxPopulation();
				pow.addContentline("\n --->>>> Best Solutions stored and used to increase pvs <<<<---\n");
				for(Solution s: auxPopulation) {
					pow.logSolution(s);
				}
				try {
					pow.writeInFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//---- updateProbabilities();
				for(Solution s: auxPopulation) {
					f.updatePossibilities(s.getPossibilityKeySet());
				}
				auxPopulation.clear();
				
				//---- logBestSolution();
				pow.addContentline("\n --->>>> Best Solution for " + actualDataset + " - according mean error <<<<---\n");
				pow.logSolutionAccuracyFirst(bestSolution);
				try {
					pow.writeInFile();
				} catch (IOException e) {
					e.printStackTrace();
				}				
				
				//---- outputSave();
				try {
					pow.saveAndClose();
				} catch (IOException e) {
					e.printStackTrace();
				}
				pww.clearSolutionLists();
			}	
			String result = pww.bestResultWekaFormatAsString(bestSolution);
			System.out.println(result);
			pow.addContentline(result);
			try {
				pow.writeInFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			pow.saveAndClose();
		}
	}
	
	public void logBadSolutions() {
		
	/*	String badSolutionsLogPath = "results/badSolutions/";;
		FileOutputWriter badSolutionsLogger = new FileOutputWriter(badSolutionsLogPath + "badSolutions_"+ dataset);
		badSolutionsLogger.addContentline("------------------------------------------------------------------------");
		badSolutionsLogger.addContentline("\n --->>>> Bad Solutions at generation: " + j);
		badSolutionsLogger.addContentline("------------------------------------------------------------------------");
		for(Solution s: pww.getSolutions()) {
			if(!s.isGoodSolution()) {
				badSolutionsLogger.addContentline(s.getPossibilityKeySet().toString());
			}
		}
		badSolutionsLogger.writeInFile();
		badSolutionsLogger.saveAndClose();*/
	}
	
}
