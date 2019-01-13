package br.ufrn.imd.experiments;

public class RunAutoWEKA {

	public static void main(String[] args) throws Exception {
		ExperimenterBase exp = new ExperimenterBase();
		exp.maxMinutes = 1; 						// in minutes
		exp.numRepetitions = 1;					// default = 10 mas, preciso baixar p/ 1
		exp.numFoldsEvaluate = 2;				// número de folds 10 default
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
		exp.output_name = "exp_autoweka";	// nome do arquivo csv
		exp.experimenter(AutoClassifier.Auto_WEKA);
	}
}
