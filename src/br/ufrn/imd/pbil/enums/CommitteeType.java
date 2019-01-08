package br.ufrn.imd.pbil.enums;

public enum CommitteeType {

	ADA_BOOST("AdaBoost"), 
	BAGGING("Bagging"), 
	RANDOM_COMMITTEE("RandomCommittee"), 
	RANDOM_FOREST("RandomForest"), 
	STACKING("Stacking"), 
	VOTE("Vote");

	private String info;

	CommitteeType(String info){
	        this.info = info;
	    }

	public String getInfo() {
		return this.info;
	}

}
