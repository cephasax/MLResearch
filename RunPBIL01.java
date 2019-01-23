package experiments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;

import weka.classifiers.meta.PopulationBasedIncrementalLearning;
import weka.classifiers.meta.pbil.Solve;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;

public class RunPBIL01 {

	private static int maxTime = 2; 						// in minutes
	private static int numRepetitions = 5;					// default = 10 mas, preciso baixar p/ 1
	private static int numFoldsEvaluate = 5;				// número de folds 10 default
	private static int numFoldsFitness = 10;
	private static String output_name = "PBIL-1.1_exp1";	// nome do arquivo csv
	private static int[] iterations = { 20 };  				// gerações = { 20, 50, 100 };
	private static int[] population_size = { 50};			// população = {10, 20, 30, 50, 70, 100}
	private static double[] population_update = { 0.5 };	// Update em % {0.1, 0.2, 0.3, 0.4, 0.5}
	private static double[] learning = { 0.5}; 				//private static double[] learning = { 0.2, 0.1, 0.05, 0.01 };
	
	private static int maxSecondsBySolution = (maxTime * 60) / 12;
	private static int numberRuns = 0;
	private static int numberInternalRuns = 0;
	
	//private static String[] base_name = {"resources/Sonar.arff", "resources/Arrhythmia.arff", "resources/Ecoli.arff", "resources/Nursery.arff", "resources/Adult.arff", 
	//		"resources/Abalone.arff", "resources/Car.arff", "resources/Credit-g.arff", "resources/KR-vs-KP.arff", "resources/Madelon.arff", 
	//		"resources/Secom.arff", "resources/Semeion.arff", "resources/Waveform.arff", "resources/Wine.arff", "resources/Yeast.arff"};
	private static String[] base_name = { "resources/Nursery.arff"};
	
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
								run(output, base, popSize, (int) Math.max(1, popUpdate * popSize), rate, steps, (i*127 + 123), maxSecondsBySolution);
								//run(output, base, popSize, (int) Math.max(1, popUpdate * popSize), rate, steps, (i*2 + 123), maxSecondsBySolution);
								//run(output, base, popSize, (int) Math.max(1, popUpdate * popSize), rate, steps, seed);
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

	private static void run(File output, String base, int popSize, int popUpdate, double learning, int iterations, int seed, int maxSeconds) throws FileNotFoundException {
		final PopulationBasedIncrementalLearning classifier = new PopulationBasedIncrementalLearning();
		classifier.setTimeLimit(maxTime);
		classifier.setNumFolds(numFoldsFitness);
		classifier.setGenerations(iterations);
		classifier.setLearningRate(learning);
		classifier.setNumSamplesUpdate(popUpdate);
		classifier.setPopulation(popSize);
		classifier.setSeed(seed);
		classifier.setMaxSecondsBySolveEvaluation(maxSeconds);
		
		double error = 0;
		double steps = 0;
		double time = 0;
		double[] measures = null;
		Solve bestSolve = null;
		AbstractClassifier bestConfiguration = null;
		int runningFold = 0;
		
		try {
			final Random rand = new Random(seed);
			final Instances instances = new Instances(new FileReader(base));
			instances.setClassIndex(instances.numAttributes() - 1);
			instances.randomize(rand);
			instances.stratify(numFoldsEvaluate);

			for (int fold = 0; fold < numFoldsEvaluate; fold++) {
				int[][] matrix = new int[instances.numClasses()][instances.numClasses()];
				runningFold = fold;
				
				// chamada do 10 fold/CV
				classifier.buildClassifier(instances.trainCV(numFoldsEvaluate, fold));
				final Instances test = instances.testCV(numFoldsEvaluate, fold);

				int miss = 0;
				for (int i = 0; i < test.numInstances(); i++) {
					int correct = (int) test.get(i).classValue();
					int predicted = (int) classifier.classifyInstance(test.get(i));
					matrix[correct][predicted]++;
					if (correct != predicted) {
						miss++;
					}
				}
				double localError = miss / (double) test.numInstances();
				error += miss / (double) test.numInstances();
				steps += classifier.getPerformedGenerations();
				time += classifier.getPerformedTime();
				
				Solve best = classifier.getBestSolves().get(0);
				if(bestSolve == null || best.compareTo(bestSolve) < 0){
					bestSolve = best.clone();
					bestConfiguration = (AbstractClassifier) best.getClassifier();
				}
				
				numberInternalRuns++;
				System.out.println("Number of Internal Runs: " + numberInternalRuns + " - Error => " + localError);
				
				if (measures == null) {
					measures = new Measures(instances, matrix).toArray();
				} else {
					double[] mNew = new Measures(instances, matrix).toArray();
					for (int i = 0; i < measures.length; i++) {
						measures[i] += mNew[i];
					}
				}
				
			}
			error = error / (double) numFoldsEvaluate;
			steps = steps / (double) numFoldsEvaluate;
			time = time / (double) numFoldsEvaluate;
			
			for (int i = 0; i < measures.length; i++) {
				measures[i] /= (double) numFoldsEvaluate;
			}
		} catch (Exception e) {
			bestSolve = null;
		}
		
		
		
		final String configuration = classifier.getClass().getName() + " " + Utils.arrayToString(classifier.getOptions()).replaceAll(",", " ");
		PrintStream out = new PrintStream(new FileOutputStream(output, true));
		if (bestConfiguration != null) {
			StringBuilder str = new StringBuilder(2000);
			String bestConfigurationParans = bestConfiguration.getClass().getName() + " " + Utils.arrayToString(bestConfiguration.getOptions()).replaceAll(",", " ");
			str.append(String.format("%.4f,%.4f,%.4f,\"%s\",\"%s\",\"%s\"", error, steps, time, base, configuration, bestConfigurationParans));
			for (double d : measures) {
				str.append(", " + d);
			}
			out.println(str.toString());
		} else {
			out.format("%.4f,%.4f,%.4f,\"%s\",\"%s\",\"%s\"\n", Double.NaN, Double.NaN, Double.NaN, base, configuration, "Error on Fold " + runningFold);
		}
		out.close();
	}
	

public static class Measures implements Serializable {

	private static final long serialVersionUID = 1L;

	private double[] precision;
	private double[] recall;
	private double[] fmeasure;
	private double[] classesDistribution;
	private String[] labels;

	public Measures(Instances instances, int[][] matrix) throws Exception {
		int numClasses = instances.numClasses();
		double numInstances = instances.numInstances();

		classesDistribution = new double[numClasses];
		for (Instance instance : instances) {
			int c = (int) instance.classValue();
			classesDistribution[c] += 1;
		}

		precision = new double[numClasses];
		recall = new double[numClasses];
		fmeasure = new double[numClasses];
		for (int i = 0; i < numClasses; i++) {
			precision[i] = precision(i, matrix);
			recall[i] = recall(i, matrix);
			fmeasure[i] = fMeasure(precision[i], recall[i]);
			classesDistribution[i] /= numInstances;
		}

		Attribute att = instances.classAttribute();
		labels = new String[numClasses];
		for (int i = 0; i < numClasses; i++) {
			labels[i] = att.value(i);
		}
	}

	public Measures(Classifier classifier, Instances train, Instances test) throws Exception {
		Evaluation eval = new Evaluation(train);
		eval.evaluateModel(classifier, test);
		init(eval, test);
	}

	public Measures(Classifier classifier, Instances instances, int numFolds, Random rand) throws Exception {
		Evaluation eval = new Evaluation(instances);
		eval.crossValidateModel(classifier, instances, numFolds, rand);
		init(eval, instances);
	}

	private void init(Evaluation eval, Instances instances) {
		int numClasses = instances.numClasses();
		double numInstances = instances.numInstances();

		classesDistribution = new double[numClasses];
		for (Instance instance : instances) {
			int c = (int) instance.classValue();
			classesDistribution[c] += 1;
		}

		precision = new double[numClasses];
		recall = new double[numClasses];
		fmeasure = new double[numClasses];
		for (int i = 0; i < numClasses; i++) {
			precision[i] = fixNaN(eval.precision(i));
			recall[i] = fixNaN(eval.recall(i));
			fmeasure[i] = fixNaN(eval.fMeasure(i));
			classesDistribution[i] /= numInstances;
		}

		Attribute att = instances.classAttribute();
		labels = new String[numClasses];
		for (int i = 0; i < numClasses; i++) {
			labels[i] = att.value(i);
		}
	}

	public double[] precision() {
		return precision;
	}

	public double[] recall() {
		return recall;
	}

	public double[] fMeasure() {
		return fmeasure;
	}

	public double precisionMean() {
		return average(precision);
	}

	public double recallMean() {
		return average(recall);
	}

	public double fMeasureMean() {
		double pavg = precisionMean();
		double ravg = recallMean();
		return fMeasure(pavg, ravg);
	}

	public double precisionWeightedMean() {
		return averageByDistribution(precision);
	}

	public double recallWeightedMean() {
		return averageByDistribution(recall);
	}

	public double fMeasureWeightedMean() {
		double pwavg = precisionWeightedMean();
		double rwavg = recallWeightedMean();
		return fMeasure(pwavg, rwavg);
	}

	public double[] toArray() {
		double[] values = new double[3 * (labels.length + 2)];
		values[0] = precisionMean();
		values[1] = recallMean();
		values[2] = fMeasureMean();
		values[3] = precisionWeightedMean();
		values[4] = recallWeightedMean();
		values[5] = fMeasureWeightedMean();
		int numClasses = labels.length;
		System.arraycopy(precision(), 0, values, 6, numClasses);
		System.arraycopy(recall(), 0, values, 6 + numClasses, numClasses);
		System.arraycopy(fMeasure(), 0, values, 6 + 2 * numClasses, numClasses);
		return values;
	}

	public String toSummary() {
		int maxLength = 15;
		for (String str : labels) {
			if (str.length() > maxLength) {
				maxLength = str.length() + 2;
			}
		}

		double[][] values = { precision(), recall(), fMeasure() };

		String mask = "  %" + maxLength + "s";
		String maskValue = "  %" + maxLength + ".4f";

		StringBuilder str = new StringBuilder(2000);
		String bigmask = mask + maskValue + maskValue + maskValue + "\n";
		str.append(String.format(mask + mask + mask + mask + "\n", "Class", "Precision", "Recall", "F-Measure"));
		for (int i = 0; i < labels.length; i++) {
			str.append(String.format(bigmask, labels[i], values[0][i], values[1][i], values[2][i]));
		}
		str.append("\n");
		str.append(String.format(bigmask, "Simple AVG", precisionMean(), recallMean(), fMeasureMean()));
		str.append(String.format(bigmask, "Weighted AVG", precisionWeightedMean(), recallWeightedMean(), fMeasureWeightedMean()));

		return str.toString();
	}

	public double recall(int classIndex, int[][] matrix) {
		double correct = 0;
		double total = 0;
		for (int j = 0; j < matrix.length; j++) {
			if (j == classIndex) {
				correct += matrix[classIndex][j];
			}
			total += matrix[classIndex][j];
		}
		return total == 0 ? 0 : correct / total;
	}

	private double precision(int classIndex, int[][] matrix) {
		double correct = 0;
		double total = 0;
		for (int i = 0; i < matrix.length; i++) {
			if (i == classIndex) {
				correct += matrix[i][classIndex];
			}
			total += matrix[i][classIndex];
		}
		return total == 0 ? 0 : correct / total;
	}

	private double fMeasure(double precision, double recall) {
		double sum = precision + recall;
		return sum == 0 ? 0 : (2.0 * (precision * recall)) / sum;
	}

	private double average(double[] values) {
		double avg = 0;
		for (double d : values) {
			avg += d;
		}
		return avg / (double) values.length;
	}

	private double averageByDistribution(double[] values) {
		double avg = 0;
		for (int i = 0; i < values.length; i++) {
			avg += fixNaN(values[i] * classesDistribution[i]);
		}
		return avg;
	}
	
	private double fixNaN(double value) {
		return Double.isNaN(value) ? 0 : value;
	}
}

}
