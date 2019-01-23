package experiments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Date;
import java.util.Random;

import weka.classifiers.meta.AutoWEKAClassifier;
import weka.core.Instances;
import weka.core.Utils;


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
	private static String[] base_name = {"resources/Nursery.arff"};
	//private static String[] base_name = { "resources/Credit-g.arff", "resources/Yeast.arff", "resources/Car.arff", "resources/KR-vs-KP.arff" };
	//private static String[] base_name = { "resources/Iris.arff",  , "resources/Ionosphere.arff" };
	
	public static void main(String[] args) throws FileNotFoundException {
		final File output = new File(output_name + " " + new Date().toString().replaceAll(":", "_") + ".csv");
		final PrintStream out = new PrintStream(new FileOutputStream(output, true));
		out.println("Error,Steps,Time,Base,Parameters,\"Best Solve\"");
		out.close();
		
		for (String base : base_name) {
			for (int i = 0; i < numRepetitions; i++) {
				//run(output, base, seed, timeLimit, memLimit, nBestConfigs, parallelRuns);
				//run(output, base, (i*2 + seed), timeLimit, memLimit, nBestConfigs, parallelRuns);
				run(output, base, (i*127 + 123), timeLimit, memLimit, nBestConfigs, parallelRuns);
				numberRuns++;
				System.out.println("Number of Runs (seeds): " + numberRuns);
			}
		}
		System.exit(0);
	}

	private static void run(File output, String base, int seed, int mTime, int mem, int nBest, int pRuns) throws FileNotFoundException {
		final AutoWEKAClassifier classifier = new AutoWEKAClassifier();
		classifier.setSeed(seed);
		classifier.setTimeLimit(mTime);
		classifier.setMemLimit(mem);
		classifier.setnBestConfigs(nBest);
		classifier.setParallelRuns(pRuns);
		
		int runningFold = 0;
		double error = 0;
		double time = 0;
		double minError = Double.MAX_VALUE;
		String bestConfiguration = null;
		
		try {
			
			final Random rand = new Random(seed);
			final Instances instances = new Instances(new FileReader(base));
			instances.setClassIndex(instances.numAttributes() - 1);
			instances.randomize(rand);
			
			time = System.currentTimeMillis();
			
			for (int fold = 0; fold < numFoldsEvaluate; fold++) {
				runningFold = fold;
				
				// chamada do 10 fold/CV
				classifier.buildClassifier(instances.trainCV(numFoldsEvaluate, fold));
				final Instances test = instances.testCV(numFoldsEvaluate, fold);
				
				int miss = 0;
				for (int i = 0; i < test.numInstances(); i++) {
					if (Double.compare(test.get(i).classValue(), classifier.classifyInstance(test.get(i))) != 0) {
						miss++;
					}
				}
				
				double localError = miss / (double) test.numInstances();
				double mainError = miss / (double) test.numInstances();
				error += localError;
				if(localError < minError){
					localError = minError;
					bestConfiguration = classifier.getBestConfiguration();
				}
				
				numberInternalRuns++;
				//String str = String.format("%.4f", localError);
				System.out.println("Number of Internal Runs (cv's): " + numberInternalRuns + " - Error => " + mainError);
			}
			
			time = (System.currentTimeMillis() - time) / 60000.0; // to seconds
			
			time = time / (double) numFoldsEvaluate;
			error = error / (double) numFoldsEvaluate;
			
		} catch (Exception e) {
			bestConfiguration = null;
		}
		
		final String configuration = Utils.arrayToString(classifier.getOptions()).replaceAll(",", " ");
		PrintStream out = new PrintStream(new FileOutputStream(output, true));
		if(bestConfiguration != null){
			out.format("%.4f,%.4f,%.4f,\"%s\",\"%s\",\"%s\"\n", error, Double.NaN, time, base, configuration, bestConfiguration.replaceAll("\"", "\"\"").replaceAll(",", " "));
		} else {
			out.format("%.4f,%.4f,%.4f,\"%s\",\"%s\",\"%s\"\n", Double.NaN, Double.NaN, Double.NaN, base, bestConfiguration, "Error on Fold " + runningFold);
		}
		out.close();
	}
}
