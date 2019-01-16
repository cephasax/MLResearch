package br.ufrn.imd;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Measures implements Serializable {

	public static void main(String[] args) throws Exception {
		String path = "./resources/datasets/Abalone.arff";
		Instances instances = DataSource.read(path);
		instances.setClassIndex(instances.numAttributes() - 1);
		Classifier classifier = new IBk(10);

		//System.out.println(new Measures(classifier, instances, 10, new Random(0)).toSummary());

		instances = DataSource.read(path);
		instances.setClassIndex(instances.numAttributes() - 1);
		instances.randomize(new Random(0));
		instances.stratify(10);
		int[][] m = new int[instances.numClasses()][instances.numClasses()];
		// instances.stratify(numFolds);(10);
		for (int i = 0; i < 10; i++) {
			classifier.buildClassifier(instances.trainCV(10, i));
			for (Instance instance : instances.testCV(10, i)) {
				int p = (int) classifier.classifyInstance(instance);
				int c = (int) instance.classValue();
				m[c][p]++;
			}
		}

		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println();
		}

		// System.out.println(matrix);

		instances = DataSource.read(path);
		instances.setClassIndex(instances.numAttributes() - 1);
		System.out.println(new Measures(instances, m).toSummary());
		
		System.out.println(Arrays.toString(new Measures(instances, m).toArray())); // TODO remove print
	}

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
