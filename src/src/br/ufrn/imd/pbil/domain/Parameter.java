package br.ufrn.imd.pbil.domain;

import br.ufrn.imd.pbil.enums.ParameterType;

public class Parameter {
	
	private ParameterType type;
	private String name;
	private String value;

	public Parameter(String name, ParameterType type ) {
		this.name = name;
		this.type = type;
	}
	
	public ParameterType getType() {
		return type;
	}

	public void setType(ParameterType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return name+": "+value+";";
	}
	public void print() {
		System.out.println(this);
	}

}
