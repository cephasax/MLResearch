package br.ufrn.imd.pbil.enums;

public enum CommitteeType {

	ADA_BOOST("Ada Boost"), 
	BAGGING("Bagging"), 
	RANDOM_COMMITTEE("Random Committee"), 
	RANDOM_FOREST("Random Forest"), 
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
