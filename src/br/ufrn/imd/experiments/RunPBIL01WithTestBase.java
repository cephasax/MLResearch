package br.ufrn.imd.experiments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Date;
import java.util.Random;

import br.ufrn.imd.pbil.Solve;
import weka.classifiers.meta.PBIL_Auto_Ens_V1;
import weka.core.Instances;
import weka.core.Utils;

public class RunPBIL01WithTestBase {

	private static int maxTime = 15; 							// in minutes
	private static int numRepetitions = 1;						// default = 10 mas, preciso baixar p/ 1
	private static int numFoldsFitness = 10;
	private static String output_name = "exp1";
	private static int[] iterations = { 20 };  					// gerações = { 20, 50, 100 };
	private static int[] population_size = { 50, 70, 100 };		// população = {10, 20, 30, 50, 70, 100}
	private static double[] population_update = { 0.5 };		// Update em % {0.1, 0.2, 0.3, 0.4, 0.5}
	private static double[] learning = { 0.001, 0.05, 0.10}; 	//private static double[] learning = { 0.2, 0.1, 0.05, 0.01 };
	
	private static int numberRuns = 0;
	private static int numberInternalRuns = 0;
	
	// Bases para treinamento e teste - Principal mudança
	private static String[] base_train = { "resources/Iris.arff"};
	private static String[] base_test = { "resources/Iris.arff"};

	public static void main(String[] args) throws FileNotFoundException {	
		final File output = new File(output_name + " " + new Date().toString().replaceAll(":", "_") + ".csv");
		final PrintStream out = new PrintStream(new FileOutputStream(output, true));
		out.println("Error,Steps,Time,Base,Parameters,\"Best Solve\"");
		out.close();
		
		for(int base = 0; base < base_train.length;base++){
			for (int popSize : population_size) {
				for (double popUpdate : population_update) {
					for (double rate : learning) {
						for (int steps : iterations) {
							for (int i = 0; i < numRepetitions; i++) {
								run(output, base_train[base], base_test[base], popSize, (int) Math.max(1, popUpdate * popSize), rate, steps, i);
								numberRuns++;
								System.out.println("Number of Runs: " + numberRuns);
							}
						}
					}
				}
			}
		}
		System.exit(0);
	}

	private static void run(File output, String base_train, String base_test, int popSize, int popUpdate, double learning, int iterations, int seed) throws FileNotFoundException {
		final PBIL_Auto_Ens_V1 classifier = new PBIL_Auto_Ens_V1();
		classifier.setTimeLimit(maxTime);
		classifier.setNumFolds(numFoldsFitness);
		classifier.setGenerations(iterations);
		classifier.setLearningRate(learning);
		classifier.setNumSamplesUpdate(popUpdate);
		classifier.setPopulation(popSize);
		classifier.setSeed(seed);
		
		double error = 0;
		double steps = 0;
		double time = 0;
		Solve bestSolve = null;
		try {
			final Random rand = new Random(seed);
			final Instances train = new Instances(new FileReader(base_train));
			train.setClassIndex(train.numAttributes() - 1);
			train.randomize(rand);
			final Instances test = new Instances(new FileReader(base_test));
			test.setClassIndex(test.numAttributes() - 1);

			classifier.buildClassifier(train);

			int miss = 0;
			for (int i = 0; i < test.numInstances(); i++) {
				if (Double.compare(test.get(i).classValue(), classifier.classifyInstance(test.get(i))) != 0) {
					miss++;
				}
			}
			error = miss / (double) test.numInstances();
			steps = classifier.getPerformedGenerations();
			time = classifier.getPerformedTime();
			
			bestSolve = classifier.getBestSolves().get(0).clone();
			
			numberInternalRuns++;
			System.out.println("Number of Internal Runs: " + numberInternalRuns);
		
		} catch (Exception e) {
			bestSolve = null;
		}
		
		final String configuration = Utils.arrayToString(classifier.getOptions()).replaceAll(",", " ");
		PrintStream out = new PrintStream(new FileOutputStream(output, true));
		if(bestSolve != null){
			out.format("%.4f,%.4f,%.4f,\"%s\",\"%s\",\"%s\"\n", error, steps, time, base_train, configuration, bestSolve.getParans().replaceAll("\"", "\"\""));
		} else {
			out.format("%.4f,%.4f,%.4f,\"%s\",\"%s\",\"%s\"\n", Double.NaN, Double.NaN, Double.NaN, base_train, configuration, "Error");
		}
		out.close();
	}
}