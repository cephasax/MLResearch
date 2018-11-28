package br.ufrn.imd.pbil.enums;

public enum ClassifierType {
	
	BASE_CLASSIFIER("base classifier"),
	COMMITTEE("committee");
	
	private String info;

	ClassifierType(String info){
        this.info = info;
    }

    public String getInfo(){
        return this.info;
    }
}
