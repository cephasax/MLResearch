package br.ufrn.imd.experiments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Date;
import java.util.Random;

import br.ufrn.imd.Measures;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.meta.AutoWEKAClassifier;
import weka.classifiers.meta.PBIL_Auto_Ens_V1;
import weka.classifiers.meta.PBIL_Auto_Ens_V2;
import weka.core.Instances;
import weka.core.Utils;

class AutoWEKAClassifier_Adapter extends AutoWEKAClassifier {

	private static final long serialVersionUID = 1L;
	
	protected double performedTime = 0;

	public AbstractClassifier getClassifier() {
		return (AbstractClassifier) classifier;
	}

	public double getPerformedTime() {
		return performedTime;
	}

	public int getPerformedStesp() {
		return 0;
		// return totalTried;
	}

	public void buildClassifier(Instances is) throws Exception {
		long startTime = System.currentTimeMillis();
		super.buildClassifier(is);
		performedTime = (System.currentTimeMillis() - startTime) / 60000.0;
	}
}

public class ExperimenterBase {
	
	private static final String DIR = "./resources/datasets/";
	
	public static final String [] set1 = {DIR + "Ecoli.arff",DIR + "Sonar.arff",DIR + "KR-vs-KP.arff",DIR + "Semeion.arff",DIR + "Adult.arff"};
	public static final String [] set2 = {DIR + "Car.arff",DIR + "GermanCredit.arff",DIR + "Wine.arff",DIR + "Waveform.arff",DIR + "Madelon.arff"};
	public static final String [] set3 = {DIR + "Yeast.arff",DIR + "Abalone.arff",DIR + "Arrhythmia.arff",DIR + "Nursery.arff",DIR + "Secom.arff"};

	public int maxMinutes = 60; 						// in minutes
	public int maxSecondsBySolve = (maxMinutes * 60) / 12; 						// in seconds
	public int numRepetitions = 2;					// default = 10 mas, preciso baixar p/ 1
	public int numFoldsEvaluate = 5;				// n�mero de folds 10 default
	public int[] iterations = { 20 };  				// gera��es = { 20, 50, 100 };
	public int[] population_size = { 30 };			// popula��o = {10, 20, 30, 50, 70, 100}
	public double[] population_update = { 0.5 };	// Update em % {0.1, 0.2, 0.3, 0.4, 0.5}
	public double[] learning = { 0.5 }; 				// private static double[] learning = { 0.2, 0.1, 0.05, 0.01 };
	public String[] bases = {
			DIR + "Ecoli.arff",
			DIR + "Car.arff",
			DIR + "Yeast.arff",
			
			DIR + "Sonar.arff",
			DIR + "GermanCredit.arff",
			DIR + "Abalone.arff",
			
			DIR + "KR-vs-KP.arff",
			DIR + "Wine.arff",
			DIR + "Arrhythmia.arff",
			
			DIR + "Semeion.arff",
			DIR + "Waveform.arff",
			DIR + "Nursery.arff",
			
			DIR + "Adult.arff",
			DIR + "Madelon.arff",
			DIR + "Secom.arff"
	};
	
	public String output_name = "exp";	// nome do arquivo csv

	private static int numFoldsFitness = 10;
	private String output_file;

	private void createResultsLog() throws FileNotFoundException {
		output_file = output_name + " " + new Date().toString().replaceAll(":", "_") + ".csv";
		final File output = new File(output_file);
		final PrintStream out = new PrintStream(new FileOutputStream(output, true));
		out.println("Error,Steps,Time,Base,Parameters,\"Best Solve\",\"Precision Simple AVG\",\"Recall Simple AVG\",\"F-Measure Simple Avg\",\"Precision Weighted AVG\",\"Recall Weighted AVG\",\"F-Measure Weighted Avg\",\"Precision Recall F-Measure by Class\"");
		out.close();
	}

	public void experimenter(AutoClassifier auto) throws Exception {
		createResultsLog();
		switch (auto) {
		case Auto_WEKA:
			for (int bIndex = 0; bIndex < bases.length; bIndex++) {
				String base = bases[bIndex];
				for (int i = 0; i < numRepetitions; i++) {
					AutoWEKAClassifier_Adapter classifier = auto.classifier();
					classifier.setSeed(i + bIndex * numRepetitions);
					classifier.setTimeLimit(maxMinutes);
					classifier.setMemLimit(4096);
					classifier.setnBestConfigs(1);
					classifier.setParallelRuns(1);
					run(auto, output_file, base, i);
				}
			}
			break;
		case PBIL_Auto_Ens_v1:
		case PBIL_Auto_Ens_v2:

			for (String base : bases) {
				for (int pIndex = 0; pIndex < population_size.length; pIndex++) {
					int popSize = population_size[pIndex];
					for (int uIndex = 0; uIndex < population_update.length; uIndex++) {
						double update = population_update[uIndex];
						for (int rIndex = 0; rIndex < learning.length; rIndex++) {
							double rate = learning[rIndex];
							for (int sIndex = 0; sIndex < iterations.length; sIndex++) {
								int step = iterations[sIndex];
								for (int i = 0; i < numRepetitions; i++) {
									if (auto == AutoClassifier.PBIL_Auto_Ens_v1) {
										PBIL_Auto_Ens_V1 classifier = auto.classifier();
										classifier.setTimeLimit(maxMinutes);
										classifier.setNumFolds(numFoldsFitness);
										classifier.setGenerations(step);
										classifier.setLearningRate(rate);
										classifier.setNumSamplesUpdate((int) Math.max(1, update * popSize));
										classifier.setPopulation(popSize);
										classifier.setSeed(i);
										classifier.setTimeLimitBySolveInSeconds(maxSecondsBySolve);
									} else {
										PBIL_Auto_Ens_V2 classifier = auto.classifier();
										classifier.setTimeLimit(maxMinutes);
										classifier.setNumFolds(numFoldsFitness);
										classifier.setGenerations(step);
										classifier.setLearningRate(rate);
										classifier.setNumSamplesUpdate((int) Math.max(1, update * popSize));
										classifier.setPopulation(popSize);
										classifier.setSeed(i);
										classifier.setTimeLimitBySolveInSeconds(maxSecondsBySolve);
									}
									run(auto, output_file, base, i);
								}
							}
						}
					}
				}
			}
		}
	}

	private void run(AutoClassifier auto, String outputPath, String basePath, int seed) throws Exception {
		double error = 0;
		double steps = 0;
		double time = 0;
		double minError = 0;
		double [] measures = null;
		String bestConfiguration = null;

		int fold = 0;
		AbstractClassifier classifier = auto.classifier();
		try {
			final Random rand = new Random(seed);
			final Instances instances = new Instances(new FileReader(basePath));
			instances.setClassIndex(instances.numAttributes() - 1);
			instances.randomize(rand);
			instances.stratify(numFoldsEvaluate);

			for (fold = 0; fold < numFoldsEvaluate; fold++) {
				int[][] matrix = new int[instances.numClasses()][instances.numClasses()];
				System.out.print("Running Fold " + fold + " for " + basePath + " :: ");

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

				error += miss / (double) test.numInstances();
				steps += auto.performedSteps();
				time += auto.performedTime();

				if (bestConfiguration == null || miss < minError) {
					minError = miss;
					bestConfiguration = auto.bestConfiguration();
				}

				System.out.format("Fold Execution %d - Error %.4f\n", fold, (miss / (double) test.numInstances()));
				
				if(measures == null) {
					measures = new Measures(instances, matrix).toArray();
				} else {
					double [] mNew = new Measures(instances, matrix).toArray();
					for(int i=0;i<measures.length;i++) {
						measures[i] += mNew[i];
					}
				}
			}
			error = error / (double) numFoldsEvaluate;
			steps = steps / (double) numFoldsEvaluate;
			time = time / (double) numFoldsEvaluate;
			for(int i=0;i<measures.length;i++) {
				measures[i] /= (double) numFoldsEvaluate;
			}
		} catch (Exception e) {
			bestConfiguration = null;
		}

		final String configuration = classifier.getClass().getName() + " " + Utils.arrayToString(classifier.getOptions()).replaceAll(",", " ");
		PrintStream out = new PrintStream(new FileOutputStream(outputPath, true));
		if (bestConfiguration != null) {
			StringBuilder str = new StringBuilder(2000);
			str.append(String.format("%.4f,%.4f,%.4f,\"%s\",\"%s\",\"%s\"", error, steps, time, basePath, configuration, bestConfiguration));
			for (double d : measures) {
				str.append(", " + d);
			}
			out.println(str.toString());
		} else {
			out.format("%.4f,%.4f,%.4f,\"%s\",\"%s\",\"%s\"\n", Double.NaN, Double.NaN, Double.NaN, basePath, configuration, "Error on Fold " + fold);
		}
		out.close();
	}

}

enum AutoClassifier {

	PBIL_Auto_Ens_v1(new PBIL_Auto_Ens_V1()),
	PBIL_Auto_Ens_v2(new PBIL_Auto_Ens_V2()),
	Auto_WEKA(new AutoWEKAClassifier_Adapter());

	private final AbstractClassifier classifier;

	AutoClassifier(AbstractClassifier classifier) {
		this.classifier = classifier;
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractClassifier> T classifier() {
		return (T) classifier;
	}

	public int performedSteps() {
		switch (this) {
		case Auto_WEKA:
			AutoWEKAClassifier_Adapter auto = (AutoWEKAClassifier_Adapter) classifier;
			return auto.getPerformedStesp();
		case PBIL_Auto_Ens_v1:
			PBIL_Auto_Ens_V1 pv1 = (PBIL_Auto_Ens_V1) classifier;
			return pv1.getPerformedGenerations();
		case PBIL_Auto_Ens_v2:
			PBIL_Auto_Ens_V2 pv2 = (PBIL_Auto_Ens_V2) classifier;
			return pv2.getPerformedGenerations();
		}
		return -1;
	}

	public double performedTime() {
		switch (this) {
		case Auto_WEKA:
			AutoWEKAClassifier_Adapter auto = (AutoWEKAClassifier_Adapter) classifier;
			return auto.getPerformedTime();
		case PBIL_Auto_Ens_v1:
			PBIL_Auto_Ens_V1 pv1 = (PBIL_Auto_Ens_V1) classifier;
			return pv1.getPerformedTime();
		case PBIL_Auto_Ens_v2:
			PBIL_Auto_Ens_V2 pv2 = (PBIL_Auto_Ens_V2) classifier;
			return pv2.getPerformedTime();
		}
		return -1;
	}

	public String bestConfiguration() {
		AbstractClassifier c = null;
		switch (this) {
		case Auto_WEKA:
			AutoWEKAClassifier_Adapter auto = (AutoWEKAClassifier_Adapter) classifier;
			c = auto.getClassifier();
			break;
		case PBIL_Auto_Ens_v1:
			PBIL_Auto_Ens_V1 pv1 = (PBIL_Auto_Ens_V1) classifier;
			try {
				c = (AbstractClassifier) (pv1.getBestSolves().get(0).getClassifier());
			} catch (Exception e) {
				e.printStackTrace();
				throw new Error(e);
			}
			break;
		case PBIL_Auto_Ens_v2:
			PBIL_Auto_Ens_V2 pv2 = (PBIL_Auto_Ens_V2) classifier;
			c = (AbstractClassifier) pv2.getBestSolution().getClassifier();
			break;
		}
		String[] config = c.getOptions();
		return c.getClass().getName() + " " + Utils.arrayToString(config).replaceAll(",", " ");
	}
}