package br.ufrn.imd.pbil.douglas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import br.ufrn.imd.pbil.Conversor;
import br.ufrn.imd.pbil.domain.Factory;
import br.ufrn.imd.pbil.domain.Individual;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;
import br.ufrn.imd.pbil.fileManipulation.PbilOutputWriter;
import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import br.ufrn.imd.pbil.pde.Solution;
import weka.classifiers.Classifier;

public class Pbil {

	
	private static int populationSize = 2;										// population
	private static int maxMinutes = 15;											// tempo de execu��o
	private static int generations = 20;										// no. gera��es
	private static int numBestSolves = 1;										// no. de solu��es
	private static int numSamplesUpdate = 25;									// tamanho do vetor de melhores individuos
	private static int numFolds = 10;											// no. de folds do CV
	private static boolean stratify = false;									// estratifica��o da base (false - defaut)
	private static int maxSecondsBySolveEvaluation = (maxMinutes * 60) / 12; 	// quantidade m�xima de segundos para avalia��o de uma �nica solu��o
	private static String log = "PBIL-";									// saida de texto para log
	
	public static float learningRate = (float) 0.5;
	
	private static Conversor conversor;
	private static Factory f;
	private static ArrayList<Solution> population;
	private static ArrayList<Solution> auxPopulation;
	private static ArrayList<String> dataSets;
	private static PbilOutputWriter pow;
	private static PbilWekaWorker pww;
	private static ArrayList<Classifier> classifiers;
	
	
	public static void main(String[] args) throws Exception {

		buildVariables();
		
		buildDatasetPaths();
		
		for(String dataset: dataSets) {
			
			buildPbilWorker(dataset);
			
			for(int j = 1; j <= generations; j++) {
				
				outputStuffAboutRunning(dataset, j);
				
				buildSolutions();
				
				logPopulation();
				
				buildWekaSolutions();
				
				
				
				
				outputSave();
			}	
		}

		
		
		
	}

	public static void buildVariables() throws InvalidParameterTypeException{
		
		f = new Factory();
		f.setLearningRate(learningRate);
		population = new ArrayList<Solution>();
		auxPopulation = new ArrayList<Solution>();
		dataSets = new ArrayList<String>();
		classifiers = new ArrayList<Classifier>();
		
		try {
			pow = new PbilOutputWriter("results/" + log);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void buildPbilWorker(String dataset) {
		try {
			pww = new PbilWekaWorker(dataset);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void buildDatasetPaths(){
		dataSets.add("datasets/x.arff");
	}
	
	public static void buildWekaSolutions(ArrayList<Solution> pop) {
		for(Solution solution: pop) {
			try {
				solution.setAccuracy(conversor.runSolution(new Individual()));
			} catch (Exception e) {
			}
		}
	}
	
	public static void buildSolutions() {
	
		int extraSolutions = (int) populationSize / 2;
		int total = populationSize + extraSolutions;
		PossibilityKeySet s;
		
		for(int i = 0; i < total;i++) {
			s = f.buildSolutionFromWeightedDraw();
			population.add(new Solution(s));
		}
		pww.setSolutions(population);
	}
	
	public static void buildWekaSolutions() throws Exception {
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

}
