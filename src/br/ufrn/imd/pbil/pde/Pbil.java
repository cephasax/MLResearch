package br.ufrn.imd.pbil.pde;

import java.util.ArrayList;

import br.ufrn.imd.pbil.Conversor;
import br.ufrn.imd.pbil.domain.Factory;
import br.ufrn.imd.pbil.domain.Individual;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class Pbil {

	
	private static int populationSize = 50;										// population
	private static int maxMinutes = 15;											// tempo de execução
	private static int generations = 20;										// no. gerações
	private static int numBestSolves = 1;										// no. de soluções
	private static int numSamplesUpdate = 25;									// tamanho do vetor de melhores individuos
	private static int numFolds = 10;											// no. de folds do CV
	private static boolean stratify = false;									// estratificação da base (false - defaut)
	private static int maxSecondsBySolveEvaluation = (maxMinutes * 60) / 12; 	// quantidade máxima de segundos para avaliação de uma única solução
	private static String log = "pbil-log.txt";									// saida de texto para log
	
	public static float learningRate = (float) 0.5;
	
	private static Conversor conversor;
	private static Factory f;
	private static ArrayList<Solution> population;
	private static ArrayList<Solution> auxPopulation;
		
	public static void main(String[] args) throws InvalidParameterTypeException {

		buildVariables();
		
		buildDatasetPaths();
		
		for(int i = 0 to number of datasets) {
			for(int j to generations) {
				
				criarSolucoes();
				
				Logar Solucoes();
				
				converter populacao para o weka();
				
				exexecutar todas sobre a base();
				
				ordenar soluções();
				
				logar resultados();
				
				guardar n melhores soluções();
				
				logar resultados();
				
				atualizar pvs();
				
				guardar melhor solução();
				
				limpar população()
				
			}	
		}
		
		
		
		PossibilityKeySet s;
		//CREATE POPULATION - populationSize Solutions
		for(int i = 0; i < populationSize; i++) {
			s = f.buildSolutionFromWeightedDraw();
			population.add(new Solution(s));
		}
		
		//LOG POPULATION
		
		//
		
		
		
	}

	public static void buildVariables() throws InvalidParameterTypeException {
		f = new Factory();
		f.setLearningRate(learningRate);
		population = new ArrayList<Solution>();
		auxPopulation = new ArrayList<Solution>();
		wrongSolutions = 0;
	}
	
	public static void buildDatasetPaths(){
		try {
			conversor = new Conversor("/resources/x.arff");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void buildWekaSolutions(ArrayList<Solution> pop) {
		for(Solution solution: pop) {
			try {
				solution.setAccuracy(conversor.runSolution(new Individual()));
			} catch (Exception e) {
				wrongSolutions++;
			}
		}
	}
	
		

}
