package br.ufrn.imd;

import java.io.Serializable;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Measures implements Serializable {

	public static void main(String[] args) throws Exception {
		Instances instances = DataSource.read("./resources/datasets/Sonar.arff");
		instances.setClassIndex(instances.numAttributes() - 1);
		Classifier classifier = new NaiveBayes();
		System.out.println(new Measures(classifier, instances, 10, new Random(0)).toSummary());
	}
	
	private static final long serialVersionUID = 1L;

	private double[] precision;
	private double[] recall;
	private double[] fmeasure;
	private double[] classesDistribution;
	private String[] labels;
	
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
	
	private void init (Evaluation eval, Instances instances) {
		int numClasses = instances.numClasses();
		double numInstances = instances.numInstances();
		precision = new double[numClasses];
		recall = new double[numClasses];
		fmeasure = new double[numClasses];
		classesDistribution = eval.getClassPriors();
		for (int i = 0; i < numClasses; i++) {
			precision[i] = eval.precision(i);
			recall[i] = eval.recall(i);
			fmeasure[i] = eval.fMeasure(i);
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

	public String toSummary() {
		int maxLength = 15;
		for(String str : labels) {
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
		for(int i=0;i<labels.length;i++) {
			str.append(String.format(bigmask, labels[i], values[0][i], values[1][i], values[2][i]));
		}
		str.append("\n");
		str.append(String.format(bigmask, "Simple AVG", precisionMean(), recallMean(), fMeasureMean()));
		str.append(String.format(bigmask, "Weighted AVG", precisionWeightedMean(), recallWeightedMean(), fMeasureWeightedMean()));

		return str.toString();
	}

	private double fMeasure(double precision, double recall) {
		return (2.0 * (precision * recall)) / (precision + recall);
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
			avg += values[i] * classesDistribution[i];
		}
		return avg;
	}
}
