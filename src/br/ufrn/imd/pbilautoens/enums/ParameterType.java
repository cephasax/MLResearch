package br.ufrn.imd.pbilautoens.enums;

public enum ParameterType {
	
	BOOLEAN("boolean"),
	CHAR("char"),
	DOUBLE("double"),
	INT("int"),
	STRING("string");
	
	private String info;

	ParameterType(String info){
        this.info = info;
    }

    public String getInfo(){
        return this.info;
    }
}



