package br.ufrn.imd.pbil.enums;

public enum BaseClassifierType {

	DECISION_TABLE("Decision Table"),
	IBK("ibk"),
	J48("J48"),
	K_STAR("KStar"),
	MULTI_LAYER_PECEPTRON("Multi Layer Perceptron"),
	NAIVE_BAYES("Naive Bayes"),
	NET("NET"),
	RANDOM_TREE("Random Tree"),
	SMO("SMO");
	
	private String info;

	BaseClassifierType(String info){
        this.info = info;
    }

    public String getInfo(){
        return this.info;
    }
	
}
