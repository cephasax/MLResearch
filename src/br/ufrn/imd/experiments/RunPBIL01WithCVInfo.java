package br.ufrn.imd.experiments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import br.ufrn.imd.pbil.Solve;
import weka.classifiers.meta.PBIL_Auto_Ens_V1;
import weka.core.Instances;
import weka.core.Utils;

public class RunPBIL01WithCVInfo {

	private static int maxTime = 1; 							// in minutes
	private static int numRepetitions = 1;						// default = 10 mas, preciso baixar p/ 1
	private static int numFoldsEvaluate = 2;					// número de folds 10 default
	private static int numFoldsFitness = 2;
	private static String output_name = "exp1.iris";
	private static int[] iterations = { 2 };  					// gerações = { 20, 50, 100 };
	
	// rodar parte que falta
	private static int[] population_size = { 10 };
	//private static int[] population_size = { 50, 70, 100 };	// população = {10, 20, 30, 50, 70, 100}
	
	private static double[] population_update = { 0.5 };		// Update em % {0.1, 0.2, 0.3, 0.4, 0.5}
	
	// rodar parte que falta
	private static double[] learning = { 0.05, 0.10};
	//private static double[] learning = { 0.001, 0.05, 0.10}; 	//private static double[] learning = { 0.2, 0.1, 0.05, 0.01 };
	
	private static int numberRuns = 0;
	private static int numberInternalRuns = 0;
	
	//private static String[] base_name = { "resources/Iris.arff", "resources/Ionosphere.arff", "resources/Arrhythmia.arff", "resources/Glass.arff" };
	private static String[] base_name = { "resources/Iris.arff"};

	public static void main(String[] args) throws FileNotFoundException {	
		final File output = new File(output_name + " " + new Date().toString().replaceAll(":", "_") + ".csv");
		final PrintStream out = new PrintStream(new FileOutputStream(output, true));
		out.println("Error,Steps,Time,Base,Parameters,\"Best Solve\"");
		out.close();
		
		for (String base : base_name) {
			for (int popSize : population_size) {
				for (double popUpdate : population_update) {
					for (double rate : learning) {
						for (int steps : iterations) {
							for (int i = 0; i < numRepetitions; i++) {
								run(output, base, popSize, (int) Math.max(1, popUpdate * popSize), rate, steps, i);
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

	private static void run(File output, String base, int popSize, int popUpdate, double learning, int iterations, int seed) throws FileNotFoundException {
		final PBIL_Auto_Ens_V1 classifier = new PBIL_Auto_Ens_V1();
		classifier.setTimeLimit(maxTime);
		classifier.setNumFolds(numFoldsFitness);
		classifier.setGenerations(iterations);
		classifier.setLearningRate(learning);
		classifier.setNumSamplesUpdate(popUpdate);
		classifier.setPopulation(popSize);
		classifier.setSeed(seed);
		
		double error = 0;
		double [] errorByFold = new double[numFoldsEvaluate];
		double steps = 0;
		double time = 0;
		Solve bestSolve = null;
		int runningFold = 0;
		try {

			final Random rand = new Random(seed);
			final Instances instances = new Instances(new FileReader(base));
			instances.setClassIndex(instances.numAttributes() - 1);
			instances.randomize(rand);

			for (int fold = 0; fold < numFoldsEvaluate; fold++) {
				runningFold = fold;
				classifier.buildClassifier(instances.trainCV(numFoldsEvaluate, fold));
				final Instances test = instances.testCV(numFoldsEvaluate, fold);

				int miss = 0;
				for (int i = 0; i < test.numInstances(); i++) {
					if (Double.compare(test.get(i).classValue(), classifier.classifyInstance(test.get(i))) != 0) {
						miss++;
					}
				}
				errorByFold[fold] = miss / (double) test.numInstances();
				error += errorByFold[fold];
				steps += classifier.getPerformedGenerations();
				time += classifier.getPerformedTime();
				
				Solve best = classifier.getBestSolves().get(0);
				if(bestSolve == null || best.compareTo(bestSolve) < 0){
					bestSolve = best.clone();
				}
				
				numberInternalRuns++;
				System.out.println("Number of Internal Runs: " + numberInternalRuns);
			}
			error = error / (double) numFoldsEvaluate;
			steps = steps / (double) numFoldsEvaluate;
			time = time / (double) numFoldsEvaluate;
		} catch (Exception e) {
			bestSolve = null;
		}
		
		final String configuration = Utils.arrayToString(classifier.getOptions()).replaceAll(",", " ");
		PrintStream out = new PrintStream(new FileOutputStream(output, true));
		if(bestSolve != null){
			StringBuilder str = new StringBuilder(500);
			if(bestSolve.cvError != null){
				for(double value : bestSolve.cvError){
					str.append(String.format(Locale.ENGLISH, "%.4f", value));
					str.append(", ");
				}
				if(bestSolve.cvError.length > 0){
					str.deleteCharAt(str.length() -1);
					str.deleteCharAt(str.length() -1);
				}
			}
			
			out.format("%.4f,%.4f,%.4f,\"%s\",\"%s\",\"%s\",%s\n", error, steps, time, base, configuration, bestSolve.getParans().replaceAll("\"", "\"\""), str.toString());
		} else {
			out.format("%.4f,%.4f,%.4f,\"%s\",\"%s\",\"%s\"\n", Double.NaN, Double.NaN, Double.NaN, base, configuration, "Error on Fold " + runningFold);
		}
		out.close();
	}
}
