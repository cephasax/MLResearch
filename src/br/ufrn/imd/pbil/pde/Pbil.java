package br.ufrn.imd.pbil.pde;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import br.ufrn.imd.pbil.domain.Factory;
import br.ufrn.imd.pbil.fileManipulation.PbilOutputWriter;
import weka.core.Instances;

public class Pbil {
	
	private int populationSize = 50;									
	private int maxMinutes = 15;											
	private int generations = 20;										
																			
	private int numFolds = 10;																				
	private int maxSecondsPerSolution = (maxMinutes * 60) / 12; 	
	private String log = "PBIL-";									
	
	public float learningRate = (float) 0.5;
	
	private Factory f;		
	private ArrayList<Solution> population;
	private ArrayList<Solution> auxPopulation;
	private int updateReason = 2;					// best solutions size to improve pv (2 = 1/2, n = 1/n, where 1=population size)
	
	private Solution bestSolution;
	
	private Instances instances;
	private PbilOutputWriter pow;
	private PbilWekaWorker pww;
	
	private String actualDataset;
	
	public void run() throws Exception {
		
		
			
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
				pww = new PbilWekaWorker(instances);
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
					pow.logDetailsAboutStep(instances.relationName(), j);
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
				// TODO como é o erro, então devemos manter a solução com menor erro
				if(population.get(0).getMeanError() < bestSolution.getMeanError()) {
					bestSolution = population.get(0);
				}
				
				//---- darwinLaw();
				// TODO pode ocorrer que a quantidade de soluções válidas seja menor que essa quantidade
				for(int i = 0; i < Math.min(((int)(populationSize/ updateReason)), population.size());i++) {
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
	}
	
	public void setInstances (Instances instances) {
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
