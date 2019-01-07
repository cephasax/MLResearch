package br.ufrn.imd.pbil.enums;

public enum BaseClassifierType {

	DECISION_TABLE("weka.classifiers.rules.DecisionTable"),
	IBK("weka.classifiers.lazy.IBk"),
	J48("weka.classifiers.treesJ48"),
	K_STAR("KStar"),
	MULTI_LAYER_PECEPTRON("weka.classifiers.functions.MultilayerPerceptron"),
	NAIVE_BAYES("weka.classifiers.bayes.NaiveBayes"),
	NET("weka.classifiers.bayes.BayesNet"),
	RANDOM_TREE("weka.classifiers.trees.RandomTree"),
	SMO("weka.classifiers.functions.SMO");
	
	private String info;

	BaseClassifierType(String info){
        this.info = info;
    }

    public String getInfo(){
        return this.info;
    }
	
}
