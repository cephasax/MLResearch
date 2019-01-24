package br.ufrn.imd.experiments;

public class RunAutoWEKA {
	
	// Não é utilizada, veja scripts na raiz chamando a classe ExperimenterBase
		
	public static void main(String[] args) throws Exception {
		ExperimenterBase exp = new ExperimenterBase();
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
		
		exp.maxMinutes = 1; 						// in minutes
		exp.numRepetitions = 10;					// default = 10 mas, preciso baixar p/ 1
		exp.numFoldsEvaluate = 5;				// número de folds 10 default
		exp.output_name = "Exp_AutoWEKAClassifier";	// nome do arquivo csv
		exp.experimenter(AutoClassifier.Auto_WEKA);
	}
}
