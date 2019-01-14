package br.ufrn.imd.experiments;

public class RunV1_Set2 {

	public static void main(String[] args) throws Exception {
		ExperimenterBase exp = new ExperimenterBase();
		exp.iterations = new int[] { 500 };  				// gerações = { 20, 50, 100 }; // max to stop by time
		exp.population_size = new int[] { 50 };			// população = {10, 20, 30, 50, 70, 100}
		exp.population_update = new double[] { 0.5 };	// Update em % {0.1, 0.2, 0.3, 0.4, 0.5}
		exp.learning = new double[] { 0.5 }; 				// private static double[] learning = { 0.2, 0.1, 0.05, 0.01 };
		
		exp.bases = exp.bases = ExperimenterBase.set2;
		exp.maxMinutes = 60; 						// in minutes
		exp.maxSecondsBySolve = 75; 						// in seconds
		exp.numRepetitions = 10;					// default = 10 mas, preciso baixar p/ 1
		exp.numFoldsEvaluate = 5;				// número de folds 10 default
		exp.output_name = "Exp_PBIL_Auto_Ens_v1";	// nome do arquivo csv
		exp.experimenter(AutoClassifier.PBIL_Auto_Ens_v1);
	}
}
