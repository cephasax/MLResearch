package experiments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.AutoWEKAClassifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;

@SuppressWarnings("serial")
class AutoWEKAClassifier_Adapter2 extends AutoWEKAClassifier {

	public AbstractClassifier getClassifier() {
		return (AbstractClassifier) classifier;
	}
}

public class RunAutoWeka01 {

	private static int timeLimit = 2; 							// in minutes
	private static int numRepetitions = 5;						// default = 10
	private static int numFoldsEvaluate = 5;					// número de folds 10 default
	private static String output_name = "AutoWeka_Exp1-1";		// nome do arquivo csv
	private static int seed = 123;								// obs: testando seed = 124
	private static int memLimit = 4096;
	private static int nBestConfigs = 1;
	private static int parallelRuns = 1;

	// controle do número de excecuções
	private static int numberRuns = 0;
	private static int numberInternalRuns = 0;
	private static String[] base_name = { "resources/Nursery.arff" };
	// private static String[] base_name = { "resources/Credit-g.arff", "resources/Yeast.arff", "resources/Car.arff", "resources/KR-vs-KP.arff" };
	// private static String[] base_name = { "resources/Iris.arff", , "resources/Ionosphere.arff" };

	public static void main(String[] args) throws FileNotFoundException {
		final File output = new File(output_name + " " + new Date().toString().replaceAll(":", "_") + ".csv");
		final PrintStream out = new PrintStream(new FileOutputStream(output, true));
		out.println("Error,Steps,Time,Base,Parameters,\"Best Solve\"");
		out.close();

		for (String base : base_name) {
			for (int i = 0; i < numRepetitions; i++) {
				// run(output, base, seed, timeLimit, memLimit, nBestConfigs, parallelRuns);
				// run(output, base, (i*2 + seed), timeLimit, memLimit, nBestConfigs, parallelRuns);
				run(output, base, (i * 127 + seed), timeLimit, memLimit, nBestConfigs, parallelRuns);
				numberRuns++;
				System.out.println("Number of Runs (seeds): " + numberRuns);
			}
		}
		System.exit(0);
	}

	private static void run(File output, String base, int seed, int mTime, int mem, int nBest, int pRuns) throws FileNotFoundException {
		final AutoWEKAClassifier_Adapter2 classifier = new AutoWEKAClassifier_Adapter2();
		classifier.setSeed(seed);
		classifier.setTimeLimit(mTime);
		classifier.setMemLimit(mem);
		classifier.setnBestConfigs(nBest);
		classifier.setParallelRuns(pRuns);

		int runningFold = 0;
		double error = 0;
		double time = 0;
		double minError = Double.MAX_VALUE;
		double[] measures = null;
		AbstractClassifier bestConfiguration = null;

		try {

			final Random rand = new Random(seed);
			final Instances instances = new Instances(new FileReader(base));
			instances.setClassIndex(instances.numAttributes() - 1);
			instances.randomize(rand);
			instances.stratify(numFoldsEvaluate);

			time = System.currentTimeMillis();

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
				double mainError = miss / (double) test.numInstances();
				error += localError;
				if (localError < minError) {
					localError = minError;
					bestConfiguration = classifier.getClassifier();
				}

				numberInternalRuns++;
				// String str = String.format("%.4f", localError);
				System.out.println("Number of Internal Runs (cv's): " + numberInternalRuns + " - Error => " + mainError);

				if (measures == null) {
					measures = new Measures(instances, matrix).toArray();
				} else {
					double[] mNew = new Measures(instances, matrix).toArray();
					for (int i = 0; i < measures.length; i++) {
						measures[i] += mNew[i];
					}
				}
			}

			time = (System.currentTimeMillis() - time) / 60000.0; // to seconds

			time = time / (double) numFoldsEvaluate;
			error = error / (double) numFoldsEvaluate;
			
			for (int i = 0; i < measures.length; i++) {
				measures[i] /= (double) numFoldsEvaluate;
			}

		} catch (Exception e) {
			bestConfiguration = null;
		}

		final String configuration = classifier.getClass().getName() + " " + Utils.arrayToString(classifier.getOptions()).replaceAll(",", " ");
		PrintStream out = new PrintStream(new FileOutputStream(output, true));
		if (bestConfiguration != null) {
			StringBuilder str = new StringBuilder(2000);
			String bestConfigurationParans = bestConfiguration.getClass().getName() + " " + Utils.arrayToString(bestConfiguration.getOptions()).replaceAll(",", " ");
			str.append(String.format("%.4f,%.4f,%.4f,\"%s\",\"%s\",\"%s\"", error, Double.NaN, time, base, configuration, bestConfigurationParans));
			for (double d : measures) {
				str.append(", " + d);
			}
			out.println(str.toString());
		} else {
			out.format("%.4f,%.4f,%.4f,\"%s\",\"%s\",\"%s\"\n", Double.NaN, Double.NaN, Double.NaN, base, configuration, "Error on Fold " + runningFold);
		}
		out.close();
	}
}


class Measures implements Serializable {

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
	
	private static double fixNaN(double value) {
		return Double.isNaN(value) ? 0 : value;
	}
}
