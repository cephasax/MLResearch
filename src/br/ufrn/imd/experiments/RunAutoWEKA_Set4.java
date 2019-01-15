package br.ufrn.imd.experiments;

public class RunAutoWEKA_Set4 {
		
	public static void main(String[] args) throws Exception {
		ExperimenterBase exp = new ExperimenterBase();
		exp.bases = ExperimenterBase.set4;
		exp.maxMinutes = 60; 						// in minutes
		exp.numRepetitions = 2;					// default = 10 mas, preciso baixar p/ 1
		exp.numFoldsEvaluate = 5;				// número de folds 10 default
		exp.output_name = "Exp_AutoWEKAClassifier_set4";	// nome do arquivo csv
		exp.experimenter(AutoClassifier.Auto_WEKA);
	}
}
