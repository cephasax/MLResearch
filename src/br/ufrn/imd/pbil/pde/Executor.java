package br.ufrn.imd.pbil.pde;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import weka.classifiers.Classifier;
import weka.core.Instances;

public class Executor {

	private static final PrintStream OUT = System.out;;
	private static final PrintStream ERR = System.err;

	private static final PrintStream NULL_STD = new PrintStream(new OutputStream() {
		public void write(int b) throws IOException {
		}
	});

	public static void enablePrint(boolean enable) {
		if (enable) {
			System.setErr(ERR);
			System.setOut(OUT);
		} else {
			System.setErr(NULL_STD);
			System.setOut(NULL_STD);
		}
	}

	public static List<Float> runSolution(Instances dataset, Classifier classifier, int numFolds) throws Exception {
		List<Float> foldMinError = new ArrayList<Float>();
		float wrongClassified = 0;
		for (int fold = 0; fold < numFolds; fold++) {
			Executor.enablePrint(false);
			try {
				classifier.buildClassifier(dataset.trainCV(numFolds, fold));
			} catch (Exception e) {
				throw e;
			} finally {
				Executor.enablePrint(true);
			}
			Instances test = dataset.testCV(numFolds, fold);
			for (int i = 0; i < test.size(); i++) {
				if (Double.compare(test.get(i).classValue(), classifier.classifyInstance(test.get(i))) != 0) {
					wrongClassified++;
				}
			}
			foldMinError.add(wrongClassified / test.size());
			wrongClassified = 0;
		}

		return foldMinError;
	}
}
