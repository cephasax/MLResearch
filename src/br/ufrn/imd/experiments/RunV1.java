package br.ufrn.imd.experiments;

public class RunV1 {

	public static void main(String[] args) throws Exception {
		ExperimenterBase exp = new ExperimenterBase();
		exp.maxMinutes = 1; 						// in minutes
		exp.maxSecondsBySolve = 5; 						// in seconds
		exp.numRepetitions = 1;					// default = 10 mas, preciso baixar p/ 1
		exp.numFoldsEvaluate = 2;				// número de folds 10 default
		exp.iterations = new int[] { 20 };  				// gerações = { 20, 50, 100 };
		exp.population_size = new int[] { 30 };			// população = {10, 20, 30, 50, 70, 100}
		exp.population_update = new double[] { 0.5 };	// Update em % {0.1, 0.2, 0.3, 0.4, 0.5}
		exp.learning = new double[] { 0.01 }; 				// private static double[] learning = { 0.2, 0.1, 0.05, 0.01 };
		exp.bases = new String[] {
				"./resources/datasets/Ecoli.arff",
				"./resources/datasets/Car.arff",
				"./resources/datasets/Yeast.arff",
				"./resources/datasets/Sonar.arff",
				"./resources/datasets/GermanCredit.arff",
				"./resources/datasets/Abalone.arff",
				"./resources/datasets/KR-vs-KP.arff",
				"./resources/datasets/Wine.arff",
				"./resources/datasets/Arrhythmia.arff",
				"./resources/datasets/Semeion.arff",
				"./resources/datasets/Waveform.arff",
				"./resources/datasets/Nursery.arff",
				"./resources/datasets/Adult.arff",
				"./resources/datasets/Madelon.arff",
				"./resources/datasets/Secom.arff",
			};
		
		exp.output_name = "exp_v1";	// nome do arquivo csv
		exp.experimenter(AutoClassifier.PBIL_Auto_Ens_v1);
	}
}
